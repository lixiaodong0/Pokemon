package com.lixd.pokemon.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.lixd.pokemon.navigation.SelfNavHost

@Composable
fun PokemonApp() {
    PokemonScreen()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier, topBar = { SelfAppTopBar() }, bottomBar = { SelfAppBottomBar() }) {
        SelfAppContent()
    }
}

@Composable
fun SelfAppContent() {
    val navController = rememberNavController()
    SelfNavHost(navController = navController)
}

@Composable
fun SelfAppTopBar() {

}

@Composable
fun SelfAppBottomBar() {

}



