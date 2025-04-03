package com.example.pokeappcrp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.pokeappcrp.data.remote.ApiClient
import com.example.pokeappcrp.mvi.pokemonlist.PokemonListViewModel
import com.example.pokeappcrp.navigation.AppNavGraph
import com.example.pokeappcrp.ui.pokemonlist.PokemonListScreen
import com.example.pokeappcrp.ui.theme.PokeAppCRPTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeAppCRPTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }

            // Llamada a la API (solo prueba)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = ApiClient.apiService.getPokemonList(limit = 20, offset = 0)
                    Log.d("POKEAPI", "Pokémon recibidos: ${response.results.size}")
                    response.results.forEach {
                        Log.d("POKEMON", "${it.name} - ${it.url}")
                    }
                } catch (e: Exception) {
                    Log.e("POKEAPI_ERROR", "Error al obtener Pokémon", e)
                }
            }
        }
    }

