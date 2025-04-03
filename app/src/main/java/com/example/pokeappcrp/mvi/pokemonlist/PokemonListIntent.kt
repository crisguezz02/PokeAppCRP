package com.example.pokeappcrp.mvi.pokemonlist

sealed class PokemonListIntent {
    object LoadPokemons : PokemonListIntent()
    object SearchByType : PokemonListIntent()
    object LoadNextPage : PokemonListIntent()
    data class SearchPokemon(val query: String) : PokemonListIntent()
    data class FilterByType(val type: String) : PokemonListIntent()
    object ResetSearch : PokemonListIntent()
    object ShowTypeSelection : PokemonListIntent()
}
