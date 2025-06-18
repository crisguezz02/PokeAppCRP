package com.example.pokeappcrp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
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

@Serializable
data class PokemonTypeWrapper(
    val type: PokemonType
)

@Serializable
data class PokemonType(
    val name: String
)

@Serializable
data class PokemonAbilityWrapper(
    val ability: PokemonAbility
)

@Serializable
data class PokemonAbility(
    val name: String
)

@Serializable
data class PokemonStatWrapper(
    val stat: PokemonStat,
    @SerialName("base_stat")
    val base_stat: Int
)

@Serializable
data class PokemonStat(
    val name: String
)

@Serializable
data class PokemonSprites(
    @SerialName("front_default")
    val front_default: String? = null
)
