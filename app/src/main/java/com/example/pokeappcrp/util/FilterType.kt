package com.example.pokeappcrp.util

import androidx.compose.ui.graphics.Color
import com.example.pokeappcrp.R

data class FilterType(
    val name: String,
    val color: Color,
    val iconRes: Int
)

val typeFilterList = listOf(
    FilterType("Normal", Color(0xFFA8A77A), R.drawable.ic_type_normal),
    FilterType("Fire", Color(0xFFEE8130), R.drawable.ic_type_fire),
    FilterType("Water", Color(0xFF6390F0), R.drawable.ic_type_water),
    FilterType("Grass", Color(0xFF7AC74C), R.drawable.ic_type_grass),
    FilterType("Bug", Color(0xFFA6B91A), R.drawable.ic_type_bug),
    FilterType("Electric", Color(0xFFF7D02C), R.drawable.ic_type_electric),
    FilterType("Psychic", Color(0xFFF95587), R.drawable.ic_type_psychic),
    FilterType("Poison", Color(0xFFA33EA1), R.drawable.ic_type_poison),
    FilterType("Fairy", Color(0xFFD685AD), R.drawable.ic_type_fairy),
    FilterType("Fighting", Color(0xFFC22E28), R.drawable.ic_type_fighting),
    FilterType("Ground", Color(0xFFE2BF65), R.drawable.ic_type_ground),
    FilterType("Flying", Color(0xFFA98FF3), R.drawable.ic_type_flying),
    FilterType("Rock", Color(0xFFB6A136), R.drawable.ic_type_rock),
    FilterType("Ghost", Color(0xFF735797), R.drawable.ic_type_ghost),
    FilterType("Ice", Color(0xFF96D9D6), R.drawable.ic_type_ice),
    FilterType("Dragon", Color(0xFF6F35FC), R.drawable.ic_type_dragon),
    FilterType("Dark", Color(0xFF705746), R.drawable.ic_type_dark),
    FilterType("Steel", Color(0xFFB7B7CE), R.drawable.ic_type_steel)
)
