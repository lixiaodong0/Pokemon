package com.lixd.pokemon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lixd.pokemon.ui.home.HomeScreen

@Composable
fun SelfNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeRoute) {
        composable(HomeRoute) {
            HomeScreen()
        }
        composable(PropsRoute) {

        }
    }
}