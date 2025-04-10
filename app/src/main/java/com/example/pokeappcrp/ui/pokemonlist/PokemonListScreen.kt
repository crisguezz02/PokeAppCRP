package com.example.pokeappcrp.ui.pokemonlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.pokeappcrp.R
import com.example.pokeappcrp.mvi.pokemonlist.PokemonListState
import com.example.pokeappcrp.util.extractIdFromUrl

@Composable
fun PokemonListScreen(
    state: PokemonListState,
    onItemClick: (String) -> Unit,
    onSearch: (String) -> Unit,
    onResetSearch: () -> Unit,
    onLoadMore: () -> Unit,
    onFilterByType: (String) -> Unit
) {
    val gridState = rememberLazyGridState()
    var searchQuery by remember { mutableStateOf("") }
    var showTypeDialog by remember { mutableStateOf(false) }

    val typeColorMap = mapOf(
        "normal" to Color(0xFFA8A878),
        "fire" to Color(0xFFF08030),
        "water" to Color(0xFF6890F0),
        "electric" to Color(0xFFF8D030),
        "grass" to Color(0xFF78C850),
        "ice" to Color(0xFF98D8D8),
        "fighting" to Color(0xFFC03028),
        "poison" to Color(0xFFA040A0),
        "ground" to Color(0xFFE0C068),
        "flying" to Color(0xFFA890F0),
        "psychic" to Color(0xFFF85888),
        "bug" to Color(0xFFA8B820),
        "rock" to Color(0xFFB8A038),
        "ghost" to Color(0xFF705898),
        "dragon" to Color(0xFF7038F8),
        "dark" to Color(0xFF705848),
        "steel" to Color(0xFFB8B8D0),
        "fairy" to Color(0xFFEE99AC)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_pokemon_fondo),
            contentDescription = "Fondo Pokémon",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { newValue ->
                    searchQuery = newValue
                    if (newValue.isBlank()) {
                        onResetSearch()
                    } else {
                        onSearch(newValue)
                    }
                },
                label = { Text("Buscar Pokémon") },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.pokeball1),
                        contentDescription = "Pokeball",
                        modifier = Modifier.size(24.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Button(
                onClick = { showTypeDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Buscar por tipo")
            }

            Box(modifier = Modifier.weight(1f)) {
                when (state) {
                    is PokemonListState.Loading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }

                    is PokemonListState.Success -> {
                        val pokemons = state.pokemons
                        LazyVerticalGrid(
                            state = gridState,
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(pokemons) { pokemon ->
                                PokemonCard(
                                    pokemon = pokemon,
                                    onClick = { onItemClick(pokemon.url.extractIdFromUrl()) }
                                )
                            }

                            if (searchQuery.isBlank()) {
                                item(span = { GridItemSpan(2) }) {
                                    Button(
                                        onClick = onLoadMore,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text("Cargar más")
                                    }
                                }
                            }
                        }
                    }

                    is PokemonListState.Error -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Error: ${state.message}")
                        }
                    }

                    PokemonListState.Idle -> {}
                }
            }
        }
    }

    if (showTypeDialog) {
        Dialog(onDismissRequest = { showTypeDialog = false }) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Selecciona un tipo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val types = listOf(
                        "Fire", "Water", "Grass", "Bug", "Electric", "Rock",
                        "Ground", "Ice", "Dragon", "Dark", "Fairy", "Steel",
                        "Ghost", "Psychic", "Poison", "Flying", "Fighting", "Normal"
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.height(200.dp)
                    ) {
                        items(types) { type ->
                            val typeColor = typeColorMap[type.lowercase()] ?: Color.Gray

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(typeColor)
                                    .clickable {
                                        onFilterByType(type)
                                        showTypeDialog = false
                                    }
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = type,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { showTypeDialog = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }
}
