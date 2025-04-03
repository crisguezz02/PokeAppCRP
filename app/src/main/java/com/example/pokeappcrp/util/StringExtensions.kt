package com.example.pokeappcrp.util

fun String.extractIdFromUrl(): String {
    return this.trimEnd('/').split("/").last()
}
