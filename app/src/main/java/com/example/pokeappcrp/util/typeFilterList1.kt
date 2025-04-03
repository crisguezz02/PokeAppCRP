package com.example.pokeappcrp.util

import androidx.compose.ui.graphics.Color
import com.example.pokeappcrp.R

data class TypeFilter(
    val name: String,
    val color: Color,
    val iconRes: Int
)

val typeFilterList1 = listOf(
    TypeFilter("Normal", Color(0xFFA8A878), R.drawable.ic_type_normal),
    TypeFilter("Fire", Color(0xFFF08030), R.drawable.ic_type_fire),
    TypeFilter("Water", Color(0xFF6890F0), R.drawable.ic_type_water),
    TypeFilter("Electric", Color(0xFFF8D030), R.drawable.ic_type_electric),
    TypeFilter("Grass", Color(0xFF78C850), R.drawable.ic_type_grass),
    TypeFilter("Ice", Color(0xFF98D8D8), R.drawable.ic_type_ice),
    TypeFilter("Fighting", Color(0xFFC03028), R.drawable.ic_type_fighting),
    TypeFilter("Poison", Color(0xFFA040A0), R.drawable.ic_type_poison),
    TypeFilter("Ground", Color(0xFFE0C068), R.drawable.ic_type_ground),
    TypeFilter("Flying", Color(0xFFA890F0), R.drawable.ic_type_flying),
    TypeFilter("Psychic", Color(0xFFF85888), R.drawable.ic_type_psychic),
    TypeFilter("Bug", Color(0xFFA8B820), R.drawable.ic_type_bug),
    TypeFilter("Rock", Color(0xFFB8A038), R.drawable.ic_type_rock),
    TypeFilter("Ghost", Color(0xFF705898), R.drawable.ic_type_ghost),
    TypeFilter("Dragon", Color(0xFF7038F8), R.drawable.ic_type_dragon),
    TypeFilter("Dark", Color(0xFF705848), R.drawable.ic_type_dark),
    TypeFilter("Steel", Color(0xFFB8B8D0), R.drawable.ic_type_steel),
    TypeFilter("Fairy", Color(0xFFEE99AC), R.drawable.ic_type_fairy)
)
