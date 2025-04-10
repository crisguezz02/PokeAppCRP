package com.example.pokeappcrp.data.remote.model

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)


data class PokemonResult(
    val name: String,
    val url: String,
    val types: List<String>? = null
)