package com.example.pokeappcrp.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val abilities: List<String>,
    val stats: List<PokemonStat>,
    val imageUrl: String?,
    val description: String
)

data class PokemonStat(
    val name: String,
    val value: Int
)

