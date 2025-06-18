package com.example.pokeappcrp.ui.pokemondetail

import PokemonDetailState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pokeappcrp.R
import com.example.pokeappcrp.mvi.pokemondetail.PokemonDetailIntent

@Composable
fun PokemonDetailScreen(
    state: PokemonDetailState,
    pokemonId: String,
    onBackClick: () -> Unit,
    onLoadDetail: (String) -> Unit
) {
    val typeBackgroundMap = mapOf(
        "normal" to R.drawable.bg_normal,
        "fire" to R.drawable.bg_fire,
        "water" to R.drawable.bg_water,
        "electric" to R.drawable.bg_electric,
        "grass" to R.drawable.bg_grass,
        "ice" to R.drawable.bg_ice,
        "fighting" to R.drawable.bg_fighting,
        "poison" to R.drawable.bg_poison,
        "ground" to R.drawable.bg_ground,
        "flying" to R.drawable.bg_flying,
        "psychic" to R.drawable.bg_psychic,
        "bug" to R.drawable.bg_bug,
        "rock" to R.drawable.bg_rock,
        "ghost" to R.drawable.bg_ghost,
        "dragon" to R.drawable.bg_dragon,
        "dark" to R.drawable.bg_dark,
        "steel" to R.drawable.bg_steel,
        "fairy" to R.drawable.bg_fairy
    )

    val statIcons = mapOf(
        "hp" to "❤️",
        "attack" to "⚔️",
        "defense" to "🛡️",
        "special-attack" to "💥",
        "special-defense" to "🧱",
        "speed" to "💨"
    )

    LaunchedEffect(pokemonId) {
        onLoadDetail(pokemonId)
    }

    when (state) {
        is PokemonDetailState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is PokemonDetailState.Success -> {
            val pokemon = state.detail
            val species = state.species
            val imageUrl = pokemon.imageUrl

            val mainType = pokemon.types.firstOrNull()?.lowercase() ?: "normal"
            val backgroundRes = typeBackgroundMap[mainType] ?: R.drawable.bg_normal

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = backgroundRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    imageUrl?.let {
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = pokemon.name,
                            modifier = Modifier.size(220.dp)
                        )
                    }

                    Text(
                        text = pokemon.name.replaceFirstChar { it.uppercaseChar() },
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = "Tipos: ${pokemon.types.joinToString()}",
                        color = Color.White,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Altura: ${pokemon.height}")
                            Text("Peso: ${pokemon.weight}")
                            Text("Habilidades: ${pokemon.abilities.joinToString()}")
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Estadísticas:", fontWeight = FontWeight.Bold)
                            pokemon.stats.forEach {
                                val icon = statIcons[it.name] ?: ""
                                Text("$icon ${it.name.replace("-", " ").replaceFirstChar { c -> c.uppercase() }}: ${it.value}")
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            val description = species.flavorTextEntries
                                .firstOrNull { it.language.name == "en" }
                                ?.flavorText
                                ?.replace("\n", " ")
                                ?.replace("\u000c", " ")
                                ?: "No description found."


                            Text("Descripción:", fontWeight = FontWeight.Bold)
                            Text(text = description)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onBackClick,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("Atrás", color = Color.Black)
                    }
                }
            }
        }

        is PokemonDetailState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.message}")
            }
        }

        PokemonDetailState.Idle -> Unit
        else -> {}
    }
}
