package com.example.pokeappcrp.data.remote

import com.example.pokeappcrp.data.remote.model.PokemonDetailResponse
import com.example.pokeappcrp.data.remote.model.PokemonResponse
import com.example.pokeappcrp.data.remote.model.PokemonSpeciesResponse
import com.example.pokeappcrp.data.remote.model.PokemonTypeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResponse
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: String
    ): PokemonDetailResponse

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(
        @Path("id") id: String
    ): PokemonSpeciesResponse
    @GET("type/{type}")
    suspend fun getPokemonsByType(
        @Path("type") type: String
    ): PokemonTypeResponse
}