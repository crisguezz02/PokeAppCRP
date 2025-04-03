package com.example.pokeappcrp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokeappcrp.mvi.pokemondetail.PokemonDetailViewModel
import com.example.pokeappcrp.mvi.pokemonlist.PokemonListViewModel
import com.example.pokeappcrp.ui.pokemondetail.PokemonDetailScreen
import com.example.pokeappcrp.ui.pokemonlist.PokemonListScreen
import com.example.pokeappcrp.ui.splash.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val LIST = "pokemon_list"
    const val DETAIL = "pokemon_detail"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val listViewModel: PokemonListViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = modifier
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                navController = navController,
                pokemonListViewModel = listViewModel
            )
        }

        composable(Routes.LIST) {
            PokemonListScreen(
                viewModel = listViewModel
            ) { pokemonId ->
                navController.navigate("${Routes.DETAIL}/$pokemonId")
            }
        }

        composable("${Routes.DETAIL}/{pokemonId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("pokemonId") ?: return@composable
            val detailViewModel: PokemonDetailViewModel = viewModel()
            PokemonDetailScreen(
                viewModel = detailViewModel,
                pokemonId = id,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
