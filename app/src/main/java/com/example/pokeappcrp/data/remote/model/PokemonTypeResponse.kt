package com.example.pokeappcrp.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeResponse(
    val pokemon: List<TypePokemonEntry>
)

@Serializable
data class TypePokemonEntry(
    val pokemon: NamedApiResource
)

@Serializable
data class NamedApiResource(
    val name: String,
    val url: String
)
