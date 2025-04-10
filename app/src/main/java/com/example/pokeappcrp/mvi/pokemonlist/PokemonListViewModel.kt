package com.example.pokeappcrp.mvi.pokemonlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeappcrp.data.remote.model.PokemonResult
import com.example.pokeappcrp.domain.PokemonRepository1

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository1
) : ViewModel() {

    val intentChannel = Channel<PokemonListIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<PokemonListState>(PokemonListState.Idle)
    val state: StateFlow<PokemonListState> = _state.asStateFlow()

    private var currentOffset = 0
    private val limit = 20

    private val fullList = mutableListOf<PokemonResult>()

    init {
        handleIntents()

        viewModelScope.launch {
            Log.d("PokemonListVM", "Enviando LoadPokemons desde init")

        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                Log.d("PokemonListViewModel", "Intent recibido: $intent")
                when (intent) {
                    is PokemonListIntent.LoadPokemons -> {
                        Log.d("PokemonListViewModel", "Iniciando la carga de pokemones")
                        fetchPokemons(true)
                    }

                    is PokemonListIntent.LoadNextPage -> {
                        Log.d("PokemonListViewModel", "Cargando siguiente página de pokemones")
                        fetchPokemons(false)
                    }

                    is PokemonListIntent.SearchPokemon -> {
                        Log.d("PokemonListViewModel", "Buscando pokemones por: ${intent.query}")
                        searchPokemon(intent.query)
                    }

                    is PokemonListIntent.FilterByType -> {
                        Log.d(
                            "PokemonListViewModel",
                            "Filtrando pokemones por tipo: ${intent.type}"
                        )
                        fetchPokemonsByType(intent.type)
                    }

                    is PokemonListIntent.ResetSearch -> {
                        Log.d("PokemonListViewModel", "Reseteando búsqueda")
                        _state.value = PokemonListState.Success(fullList)
                    }

                    PokemonListIntent.ShowTypeSelection -> {
                        Log.d("PokemonListViewModel", "Mostrando selección de tipos")
                    }

                    else -> {
                        Log.d("PokemonListViewModel", "No se ha manejado este intent: $intent")
                    }
                }
            }
        }
    }

    private fun fetchPokemons(reset: Boolean) {
        viewModelScope.launch {
            _state.value = PokemonListState.Loading
            try {
                if (reset) {
                    currentOffset = 0
                    fullList.clear()
                }

                val newPokemons = pokemonRepository.getPokemonList(limit, currentOffset)
                currentOffset += limit

                fullList.addAll(newPokemons)
                _state.value = PokemonListState.Success(fullList.toList()) // Evita mutabilidad
            } catch (e: Exception) {
                _state.value = PokemonListState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
    private fun fetchPokemonsByType(type: String) {
        viewModelScope.launch {
            _state.value = PokemonListState.Loading
            try {
                val results = pokemonRepository.getPokemonsByType(type)
                _state.value = PokemonListState.Success(results)
            } catch (e: Exception) {
                _state.value = PokemonListState.Error(
                    "Error al cargar pokémon del tipo $type: ${e.localizedMessage}"
                )
            }
        }
    }

    private fun searchPokemon(query: String) {
        viewModelScope.launch {
            _state.value = PokemonListState.Loading
            try {
                val filtered = fullList.filter { pokemon ->
                    val matchesName = pokemon.name.contains(query, ignoreCase = true)
                    val matchesType = pokemon.types?.any { type ->
                        type.equals(query, ignoreCase = true)
                    } ?: false
                    matchesName || matchesType
                }
                _state.value = PokemonListState.Success(filtered)
            } catch (e: Exception) {
                _state.value = PokemonListState.Error(e.localizedMessage ?: "Error en búsqueda")
            }
        }


}
}