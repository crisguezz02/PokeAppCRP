package com.example.pokeappcrp.mvi.pokemondetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokeappcrp.mvi.pokemondetail.PokemonDetailIntent
import com.example.pokeappcrp.mvi.pokemondetail.PokemonDetailViewModel
import com.example.pokeappcrp.ui.pokemondetail.PokemonDetailScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel
@Composable
fun PokemonDetailRoute(
    pokemonId: String,
    onBackClick: () -> Unit,

     viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(pokemonId) {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.intentChannel.send(PokemonDetailIntent.LoadDetail(pokemonId))
        }
    }

    PokemonDetailScreen(
        state = state,
        pokemonId = pokemonId,
        onBackClick = onBackClick,
        onLoadDetail = { id ->
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.intentChannel.send(PokemonDetailIntent.LoadDetail(id))
            }
        }
    )
}



