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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val pokemonRepository: PokemonRepository = PokemonRepository(
        RetrofitProvider.pokemonService
    )
) : ViewModel() {

    var viewStatus: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())

    fun getPokemon(id: String = "1") {
        Log.d("lixd", "[getPokemon]onStart")
        viewStatus.value = viewStatus.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                pokemonRepository.getPokemon(id)
            }.onSuccess {
                Log.d("lixd", "[getPokemon]onSuccess  it${it.id}")
                withContext(Dispatchers.Main) {
                    viewStatus.value = HomeState(false, it)
                }
            }.onFailure {
                Log.d("lixd", "[getPokemon]onFailure", it)
                withContext(Dispatchers.Main) {
                    viewStatus.value = viewStatus.value.copy(isLoading = false, data = null)
                }
            }
        }
    }
}