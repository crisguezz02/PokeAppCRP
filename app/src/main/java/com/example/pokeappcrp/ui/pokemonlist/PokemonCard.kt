package com.example.pokeappcrp.ui.pokemonlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import coil.compose.rememberAsyncImagePainter
import com.example.pokeappcrp.data.remote.model.PokemonResult
import com.example.pokeappcrp.util.extractIdFromUrl
import com.example.pokeappcrp.util.parseTypeToDrawable
import com.example.pokeappcrp.util.parseTypeToCardBackground
import com.example.pokeappcrp.R
@Composable
fun PokemonCard(
    pokemon: PokemonResult,
    onClick: () -> Unit
) {
    val id = pokemon.url.extractIdFromUrl()
    val pokemonNumber = id.padStart(3, '0')
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

    val primaryType = pokemon.types.firstOrNull() ?: "default"
    val backgroundRes = parseTypeToCardBackground(primaryType)

    Box(
        modifier = Modifier
            .aspectRatio(0.72f)
            .clickable { onClick() }
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = "Card Background",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(110.dp)
                    .background(Color.White)
                    .border(BorderStroke(4.dp, Color(0xFFFFD700)), shape = RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(90.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "N.º $pokemonNumber",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercaseChar() },
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 11.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                pokemon.types.forEach { typeString ->
                    val iconRes = parseTypeToDrawable(typeString)
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = typeString,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(15.dp)
                    )
                }
            }
        }
    }
}
