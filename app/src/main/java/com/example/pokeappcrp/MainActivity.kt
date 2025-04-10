package com.example.pokeappcrp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.pokeappcrp.navigation.AppNavGraph
import com.example.pokeappcrp.ui.theme.PokeAppCRPTheme
import dagger.hilt.android.AndroidEntryPoint // Añadir importación para Hilt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeAppCRPTheme {
                // Crear el NavController aquí
                val navController = rememberNavController()

                // Pasar el NavController a la gráfica de navegación
                AppNavGraph(navController = navController)
            }
        }
    }
}
