package com.example.pokeappcrp.mvi.pokemonlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokeappcrp.ui.pokemonlist.PokemonListScreen

@Composable
fun PokemonListRoute(
    viewModel: PokemonListViewModel = viewModel(),
    onItemClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    PokemonListScreen(
        state = state,
        onItemClick = onItemClick,
        onSearch = { query ->
            coroutineScope.launch {
                viewModel.intentChannel.send(PokemonListIntent.SearchPokemon(query))
            }
        },
        onResetSearch = {
            coroutineScope.launch {
                viewModel.intentChannel.send(PokemonListIntent.ResetSearch)
            }
        },
        onLoadMore = {
            coroutineScope.launch {
                viewModel.intentChannel.send(PokemonListIntent.LoadNextPage)
            }
        },
        onFilterByType = { type ->
            coroutineScope.launch {
                viewModel.intentChannel.send(PokemonListIntent.FilterByType(type))
            }
        }
    )
}