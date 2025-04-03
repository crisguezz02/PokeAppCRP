package com.example.pokeappcrp.mvi.pokemonlist

import com.example.pokeappcrp.data.remote.model.PokemonResult

sealed class PokemonListState {
    object Idle : PokemonListState()
    object Loading : PokemonListState()
    data class Success(val pokemons: List<PokemonResult>) : PokemonListState()
    data class Error(val message: String) : PokemonListState()
}
