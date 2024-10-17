package com.lixd.pokemon.ui.desc

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lixd.pokemon.data.bean.PokemonBean
import com.lixd.pokemon.data.repository.PokemonRepository
import com.lixd.pokemon.events.UpdatePokemonAvatarEvent
import com.lixd.pokemon.network.retrofit.RetrofitProvider
import com.lixd.pokemon.ui.pokemon.PokemonIndexAction
import com.lixd.pokemon.ui.pokemon.PokemonIndexEvent
import com.lixd.pokemon.ui.pokemon.PokemonIndexState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class PokemonDescriptionViewModel(
    private val pokemonRepository: PokemonRepository = PokemonRepository(
        RetrofitProvider.pokemonService
    )
) : ViewModel() {

    //UI状态
    private val _viewState = MutableStateFlow(PokemonDescriptionState())
    val viewState = _viewState.asStateFlow()

    //一次性事件，例如toast
    private val _viewEvent = Channel<PokemonDescriptionEvent>(Channel.BUFFERED)
    val viewEvent = _viewEvent.receiveAsFlow()

    fun onAction(action: PokemonDescriptionAction) {
        when (action) {
            is PokemonDescriptionAction.GetPokemonData -> {
                getPokemon(action.id)
            }

            is PokemonDescriptionAction.UpdateTabIndex -> {
                _viewState.update { it.copy(currentTabIndex = action.tabIndex) }
            }
        }
    }

    private fun getPokemon(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                pokemonRepository.getPokemon(id)
            }.onSuccess { data ->
                _viewState.update { it.copy(data = data) }
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

    private fun getAbility(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                pokemonRepository.getAbility(url)
            }.onSuccess { data ->
                _viewState.update { it.copy(ability = data) }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}