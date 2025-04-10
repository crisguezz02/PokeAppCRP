package com.example.pokeappcrp.domain

import com.example.pokeappcrp.data.remote.model.PokemonResult
import com.example.pokeappcrp.data.remote.model.PokemonSpeciesResponse
import com.example.pokeappcrp.domain.model.Pokemon

interface PokemonRepository1 {
    suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonResult>
    suspend fun getPokemonDetail(id: String): Pokemon
    suspend fun getPokemonSpecies(id: String): PokemonSpeciesResponse
    suspend fun getPokemonsByType(type: String): List<PokemonResult>
}