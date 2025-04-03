package com.example.pokeappcrp.data.remote.model

data class PokemonTypeResponse(
    val pokemon: List<TypePokemonEntry>
)

data class TypePokemonEntry(
    val pokemon: NamedApiResource
)

data class NamedApiResource(
    val name: String,
    val url: String
)