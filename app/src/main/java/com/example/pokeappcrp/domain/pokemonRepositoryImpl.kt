package com.example.pokeappcrp.domain

import com.example.pokeappcrp.data.remote.PokeApiService
import com.example.pokeappcrp.data.remote.model.PokemonResult
import com.example.pokeappcrp.data.remote.model.PokemonSpeciesResponse
import com.example.pokeappcrp.domain.model.Pokemon
import com.example.pokeappcrp.domain.model.PokemonStat
import com.example.pokeappcrp.domain.PokemonRepository1
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApiService
) : PokemonRepository1 {

    override suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonResult> {
        val response = api.getPokemonList(limit, offset)

        return response.results.map { basicResult ->
            val detail = api.getPokemonDetail(basicResult.name)

            PokemonResult(
                name = basicResult.name,
                url = basicResult.url,
                types = detail.types.map { it.type.name } 
            )
        }
    }

    override suspend fun getPokemonDetail(id: String): Pokemon {
        val detail = api.getPokemonDetail(id)
        val species = api.getPokemonSpecies(id)
        val description = species.flavor_text_entries
            .firstOrNull { it.language.name == "en" }
            ?.flavor_text?.replace("\n", " ")?.replace("\u000c", " ")
            ?: "No description found."

        return Pokemon(
            id = detail.id,
            name = detail.name,
            height = detail.height,
            weight = detail.weight,
            types = detail.types.map { it.type.name },
            abilities = detail.abilities.map { it.ability.name },
            stats = detail.stats.map { PokemonStat(it.stat.name, it.base_stat) },
            imageUrl = detail.sprites.front_default,
            description = description
        )
    }

    override suspend fun getPokemonSpecies(id: String): PokemonSpeciesResponse {
        return api.getPokemonSpecies(id)
    }

    override suspend fun getPokemonsByType(type: String): List<PokemonResult> {
        val response = api.getPokemonsByType(type)
        return response.pokemon.map {
            PokemonResult(name = it.pokemon.name, url = it.pokemon.url)
        }
    }
}
