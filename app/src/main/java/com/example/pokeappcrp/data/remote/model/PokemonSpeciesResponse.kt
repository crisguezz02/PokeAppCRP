package com.example.pokeappcrp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpeciesResponse(
    @SerialName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>
) {

}

@Serializable
data class FlavorTextEntry(
    @SerialName("flavor_text")
    val flavorText: String,
    val language: Language
)

@Serializable
data class Language(
    val name: String
)
