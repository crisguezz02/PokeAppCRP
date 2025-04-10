package com.example.pokeappcrp.domain.repository

import com.example.pokeappcrp.data.remote.model.PokemonDetailResponse
import com.example.pokeappcrp.data.remote.model.PokemonSpeciesResponse
import com.example.pokeappcrp.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonDetail(id: String): Pokemon
    suspend fun getPokemonSpecies(id: String): PokemonSpeciesResponse
}