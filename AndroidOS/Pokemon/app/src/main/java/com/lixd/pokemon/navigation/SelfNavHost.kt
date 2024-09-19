package com.lixd.pokemon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lixd.pokemon.ui.home.HomeScreen
import com.lixd.pokemon.ui.pokemon.PokemonIndexScreen

@Composable
fun SelfNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = PokemonIndexRoute) {
        composable(HomeRoute) {
            HomeScreen()
        }
        composable(PokemonIndexRoute) {
            PokemonIndexScreen()
        }
        composable(PropsRoute) {

        }
    }
}