package com.example.pokeappcrp.mvi.pokemondetail

import com.example.pokeappcrp.data.remote.model.PokemonDetailResponse
import com.example.pokeappcrp.data.remote.model.PokemonSpeciesResponse

sealed class PokemonDetailState {
    object Idle : PokemonDetailState()
    object Loading : PokemonDetailState()
    data class Success(
        val detail: PokemonDetailResponse,
        val species: PokemonSpeciesResponse
    ) : PokemonDetailState()
    data class Error(val message: String) : PokemonDetailState()
}

