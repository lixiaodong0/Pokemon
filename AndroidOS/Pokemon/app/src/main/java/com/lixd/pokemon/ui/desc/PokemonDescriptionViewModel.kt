package com.lixd.pokemon.ui.desc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lixd.pokemon.data.repository.PokemonRepository
import com.lixd.pokemon.network.retrofit.RetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDescriptionViewModel(
    private val pokemonRepository: PokemonRepository = PokemonRepository(
        RetrofitProvider.pokemonService
    )
) : ViewModel() {
    val viewStatus = MutableStateFlow(PokemonDescriptionState())
    fun getPokemon(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                pokemonRepository.getPokemon(id)
            }.onSuccess { data ->
                viewStatus.update { it.copy(data = data) }
                //请求能力数据
                data.abilities?.first()?.let {
                    getAbility(it.ability.url)
                }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun getAbility(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                pokemonRepository.getAbility(url)
            }.onSuccess { data ->
                viewStatus.update { it.copy(ability = data) }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}