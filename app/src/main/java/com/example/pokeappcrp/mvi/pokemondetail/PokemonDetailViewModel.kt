package com.example.pokeappcrp.mvi.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeappcrp.data.remote.ApiClient
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {

    val intentChannel = Channel<PokemonDetailIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Idle)
    val state: StateFlow<PokemonDetailState> = _state.asStateFlow()

    init {

        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is PokemonDetailIntent.LoadDetail -> loadPokemonDetail(intent.id)
                }
            }
        }
    }

    private fun loadPokemonDetail(id: String) {
        viewModelScope.launch {
            _state.value = PokemonDetailState.Loading
            try {
                val detail = ApiClient.apiService.getPokemonDetail(id)
                val species = ApiClient.apiService.getPokemonSpecies(id)
                _state.value = PokemonDetailState.Success(detail, species)
            } catch (e: Exception) {
                _state.value = PokemonDetailState.Error(e.localizedMessage ?: "Error al cargar detalle")
            }
        }
    }
}
