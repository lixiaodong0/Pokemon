package com.lixd.pokemon.ui

import androidx.lifecycle.ViewModel
import com.lixd.pokemon.data.bean.PokemonIndexBean
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShareViewModel : ViewModel() {
    private val _pokemonIndexState = MutableStateFlow(mutableListOf<PokemonIndexBean>())
    val pokemonIndexState = _pokemonIndexState.asStateFlow()
    fun updatePokemonIndexList(list: List<PokemonIndexBean>) {
        _pokemonIndexState.value = list.toMutableList()
    }
}