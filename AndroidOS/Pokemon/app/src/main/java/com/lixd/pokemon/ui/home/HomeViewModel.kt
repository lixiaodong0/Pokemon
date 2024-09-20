package com.lixd.pokemon.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lixd.pokemon.data.repository.PokemonRepository
import com.lixd.pokemon.network.retrofit.RetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val pokemonRepository: PokemonRepository = PokemonRepository(
        RetrofitProvider.pokemonService
    )
) : ViewModel() {

    var viewStatus: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())

    fun getPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                pokemonRepository.getPokemonList()
            }.onSuccess { data ->

            }.onFailure {

            }
        }
    }

    fun getPokemon(id: Int = 1) {
        Log.d("lixd", "[getPokemon]onStart")
        viewStatus.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                pokemonRepository.getPokemon(id)
            }.onSuccess { data ->
                Log.d("lixd", "[getPokemon]onSuccess  it${data.id}")
                viewStatus.update { it.copy(isLoading = false, data) }
            }.onFailure {
                Log.d("lixd", "[getPokemon]onFailure", it)
                viewStatus.update { it.copy(isLoading = false, null) }
            }
        }
    }
}