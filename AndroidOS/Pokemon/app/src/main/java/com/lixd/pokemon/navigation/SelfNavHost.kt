package com.lixd.pokemon.navigation

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lixd.pokemon.ui.ShareViewModel
import com.lixd.pokemon.ui.desc.PokemonDescriptionScreen
import com.lixd.pokemon.ui.home.HomeScreen
import com.lixd.pokemon.ui.pokemon.PokemonIndexScreen

@Composable
fun SelfNavHost(navController: NavHostController, shareViewModel: ShareViewModel) {
    NavHost(navController = navController, startDestination = PokemonIndexRoute) {
        composable(HomeRoute) {
            HomeScreen()
        }
        composable(PokemonIndexRoute) {
            PokemonIndexScreen(navController = navController)
        }
        composable("${PokemonDescriptionRoute}/{id}", arguments = listOf(
            navArgument("id") { type = NavType.IntType }
        )) {
            val pokemonId = it.arguments?.getInt("id") ?: 0
            PokemonDescriptionScreen(
                pokemonId = pokemonId,
            )
        }
        composable(PropsRoute) {

        }
    }
}