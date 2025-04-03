package com.example.pokeappcrp.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokeappcrp.navigation.Routes
import kotlinx.coroutines.delay
import com.example.pokeappcrp.R
import com.example.pokeappcrp.mvi.pokemonlist.PokemonListIntent
import com.example.pokeappcrp.mvi.pokemonlist.PokemonListState
import com.example.pokeappcrp.mvi.pokemonlist.PokemonListViewModel
import kotlinx.coroutines.launch



@Composable
fun SplashScreen(
    navController: NavController,
    pokemonListViewModel: PokemonListViewModel
) {

    val scaleAnim = remember { Animatable(0f) }
    val progressAnim = remember { Animatable(0f) }


    val state by pokemonListViewModel.state.collectAsState()


    LaunchedEffect(Unit) {

        launch {
            scaleAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 2300, easing = EaseOutBack)
            )
        }

        launch {
            progressAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 2500, easing = LinearOutSlowInEasing)
            )
        }
    }


    LaunchedEffect(state) {
        when (state) {
            is PokemonListState.Success -> {
                navController.navigate(Routes.LIST) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
            is PokemonListState.Error -> {

            }

            else -> {}
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFF7070)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "PokeAppCRP",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.offset(y = (-24).dp)
            )

           Image(
                painter = painterResource(id = R.drawable.pokeball1),
                contentDescription = "Pokeball",
                modifier = Modifier
                    .size(250.dp)
                    .scale(scaleAnim.value)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "¡Hazte con todos!",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Loading App.....",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))


            LinearProgressIndicator(
                progress = progressAnim.value,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(6.dp)
            )
        }
    }
}
