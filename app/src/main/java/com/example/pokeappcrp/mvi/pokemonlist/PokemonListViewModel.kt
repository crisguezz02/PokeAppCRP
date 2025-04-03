package com.example.pokeappcrp.mvi.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeappcrp.data.remote.ApiClient
import com.example.pokeappcrp.data.remote.model.PokemonResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {

    val intentChannel = Channel<PokemonListIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<PokemonListState>(PokemonListState.Idle)
    val state: StateFlow<PokemonListState> = _state.asStateFlow()

    private var currentOffset = 0
    private val limit = 20

    private val fullList = mutableListOf<PokemonResult>()

    init {
        handleIntents()
        viewModelScope.launch {
            intentChannel.send(PokemonListIntent.LoadPokemons)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is PokemonListIntent.LoadPokemons -> fetchPokemons(true)
                    is PokemonListIntent.LoadNextPage -> fetchPokemons(false)
                    is PokemonListIntent.SearchPokemon -> searchPokemon(intent.query)
                    is PokemonListIntent.FilterByType -> fetchPokemonsByType(intent.type)
                    is PokemonListIntent.ResetSearch -> {
                        _state.value = PokemonListState.Success(fullList)
                    }
                    is PokemonListIntent.ShowTypeSelection -> {}
                    PokemonListIntent.SearchByType -> {}
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

                val response = ApiClient.apiService.getPokemonList(limit, currentOffset)
                val basicPokemons = response.results

                val detailedPokemons = basicPokemons.map { basic ->
                    val detail = ApiClient.apiService.getPokemonDetail(basic.name)
                    val typeNames = detail.types.map { it.type.name }
                    basic.copy(types = typeNames)
                }

                fullList.addAll(detailedPokemons)
                currentOffset += limit

                _state.value = PokemonListState.Success(fullList)
            } catch (e: Exception) {
                _state.value = PokemonListState.Error(e.localizedMessage ?: "Error desconocido")
            }
        }
    }

    private fun fetchPokemonsByType(type: String) {
        viewModelScope.launch {
            _state.value = PokemonListState.Loading
            try {
                val response = ApiClient.apiService.getPokemonsByType(type)
                val entries = response.pokemon.take(30)

                val deferred = entries.map { entry ->
                    async {
                        try {
                            val detail = ApiClient.apiService.getPokemonDetail(entry.pokemon.name)
                            val typeNames = detail.types.map { it.type.name }
                            PokemonResult(
                                name = entry.pokemon.name,
                                url = entry.pokemon.url,
                                types = typeNames
                            )
                        } catch (e: Exception) {
                            null
                        }
                    }
                }

                val results = deferred.awaitAll().filterNotNull()

                _state.value = PokemonListState.Success(results)
            } catch (e: Exception) {
                _state.value = PokemonListState.Error("Error al cargar pokémon del tipo $type: ${e.localizedMessage}")
            }
        }
    }

    private fun searchPokemon(query: String) {
        viewModelScope.launch {
            _state.value = PokemonListState.Loading
            try {
                val filtered = fullList.filter { pokemon ->
                    val matchesName = pokemon.name.contains(query, ignoreCase = true)
                    val matchesType = pokemon.types.any { type ->
                        type.equals(query, ignoreCase = true)
                    }
                    matchesName || matchesType
                }
                _state.value = PokemonListState.Success(filtered)
            } catch (e: Exception) {
                _state.value = PokemonListState.Error(e.localizedMessage ?: "Error en búsqueda")
            }
        }
    }
}
