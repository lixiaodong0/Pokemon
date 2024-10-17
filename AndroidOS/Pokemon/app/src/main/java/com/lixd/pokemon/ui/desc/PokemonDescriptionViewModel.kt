package com.lixd.pokemon.ui.desc

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lixd.pokemon.data.repository.PokemonRepository
import com.lixd.pokemon.events.UpdatePokemonAvatarEvent
import com.lixd.pokemon.network.retrofit.RetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

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

                //发送事件更新
                val avatar = data.sprites.other.officialArtwork.frontDefault
                Log.d("Events", "[sendUpdatePokemonAvatarEvent]id:${id},avatar:${avatar}")
                EventBus.getDefault().post(UpdatePokemonAvatarEvent(id, avatar))

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