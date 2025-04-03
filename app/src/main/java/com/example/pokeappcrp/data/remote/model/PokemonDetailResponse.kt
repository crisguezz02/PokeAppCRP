package com.example.pokeappcrp.data.remote.model

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTypeWrapper>,
    val abilities: List<PokemonAbilityWrapper>,
    val stats: List<PokemonStatWrapper>,
    val sprites: PokemonSprites
)

data class PokemonTypeWrapper(
    val type: PokemonType
)

data class PokemonType(
    val name: String
)

data class PokemonAbilityWrapper(
    val ability: PokemonAbility
)

data class PokemonAbility(
    val name: String
)

data class PokemonStatWrapper(
    val stat: PokemonStat,
    val base_stat: Int
)

data class PokemonStat(
    val name: String
)

data class PokemonSprites(
    val front_default: String?
)
