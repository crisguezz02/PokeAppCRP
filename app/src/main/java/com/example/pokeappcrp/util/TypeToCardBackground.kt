package com.example.pokeappcrp.util

import com.example.pokeappcrp.R

fun parseTypeToCardBackground(type: String): Int {
    return when (type.lowercase()) {
        "fire" -> R.drawable.card_fire
        "water" -> R.drawable.card_water
        "grass" -> R.drawable.card_grass
        "electric" -> R.drawable.card_electric
        "psychic" -> R.drawable.card_psychic
        "rock" -> R.drawable.card_rock
        "ground" -> R.drawable.card_ground
        "bug" -> R.drawable.card_bug1
        "normal" -> R.drawable.card_normal1
        "poison" -> R.drawable.card_poison
        "fighting" -> R.drawable.card_fighting
        "ghost" -> R.drawable.card_ghost
        "dark" -> R.drawable.card_dark
        "ice" -> R.drawable.card_ice

        else -> R.drawable.card_default
    }
}
