package com.example.pokeappcrp.mvi.pokemondetail

sealed class PokemonDetailIntent {
    data class LoadDetail(val id: String) : PokemonDetailIntent()
}
