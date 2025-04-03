package com.example.pokeappcrp.util

import com.example.pokeappcrp.R

fun parseTypeToDrawable(type: String): Int {
    return when (type.lowercase()) {
        "normal" -> R.drawable.ic_type_normal
        "fire" -> R.drawable.ic_type_fire
        "water" -> R.drawable.ic_type_water
        "electric" -> R.drawable.ic_type_electric
        "grass" -> R.drawable.ic_type_grass
        "ice" -> R.drawable.ic_type_ice
        "fighting" -> R.drawable.ic_type_fighting
        "poison" -> R.drawable.ic_type_poison
        "ground" -> R.drawable.ic_type_ground
        "flying" -> R.drawable.ic_type_flying
        "psychic" -> R.drawable.ic_type_psychic
        "bug" -> R.drawable.ic_type_bug
        "rock" -> R.drawable.ic_type_rock
        "ghost" -> R.drawable.ic_type_ghost
        "dragon" -> R.drawable.ic_type_dragon
        "dark" -> R.drawable.ic_type_dark
        "steel" -> R.drawable.ic_type_steel
        "fairy" -> R.drawable.ic_type_fairy
        else -> R.drawable.ic_type_unknown
    }
}
