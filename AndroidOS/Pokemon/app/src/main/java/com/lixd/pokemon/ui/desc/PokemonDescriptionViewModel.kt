package com.lixd.pokemon.ui.desc

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lixd.pokemon.data.bean.PokemonIndexBean
import com.lixd.pokemon.data.repository.PokemonRepository
import com.lixd.pokemon.events.UpdatePokemonAvatarEvent
import com.lixd.pokemon.network.retrofit.RetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private var pokemonList: List<PokemonIndexBean> = mutableListOf()
    private var currentId = 0

    fun onAction(action: PokemonDescriptionAction) {
        when (action) {
            is PokemonDescriptionAction.GetPokemonData -> {
                getPokemon(action.id)
            }

            is PokemonDescriptionAction.UpdateTabIndex -> {
                _viewState.update { it.copy(currentTabIndex = action.tabIndex) }
            }

            is PokemonDescriptionAction.SetPokemonList -> {
                pokemonList = action.data
            }

            is PokemonDescriptionAction.Next -> {
                nextPokemon()
            }

            is PokemonDescriptionAction.Previous -> {
                previousPokemon()
            }

            else -> {}
        }
    }

    private fun nextPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentIndex = currentIndex()
            if (currentIndex == -1) {
                return@launch
            }
            if (currentIndex + 1 >= pokemonList.size) {
                _viewEvent.send(PokemonDescriptionEvent.Toast("no more data"))
                return@launch
            }
            val nextBean = pokemonList[currentIndex + 1]
            Log.d(
                "PokemonDescriptionViewModel",
                "currentIndex:${currentIndex},nextIndex:${currentIndex + 1},nextBean:${nextBean}"
            )
            getPokemon(nextBean.number)
        }
    }

    private fun currentIndex() = run {
        val findBean = pokemonList.find { it.number == currentId }
        if (findBean != null) {
            pokemonList.indexOf(findBean)
        } else {
            -1
        }
    }


    private fun previousPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentIndex = currentIndex()
            if (currentIndex == -1) {
                return@launch
            }
            if (currentIndex - 1 < 0) {
                _viewEvent.send(PokemonDescriptionEvent.Toast("no more data"))
                return@launch
            }
            val previousBean = pokemonList[currentIndex - 1]
            Log.d(
                "PokemonDescriptionViewModel",
                "currentIndex:${currentIndex},previousIndex:${currentIndex - 1},previousBean:${previousBean}"
            )
            getPokemon(previousBean.number)
        }
    }

    private fun getPokemon(id: Int) {
        currentId = id
        calculateCurrentIndicator()
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

    private fun calculateCurrentIndicator() {
        val currentIndex = currentIndex()
        if (currentIndex != -1) {
            val canNext = currentIndex + 1 < pokemonList.size
            val canPrevious = currentIndex - 1 >= 0
            val currentBean = pokemonList[currentIndex]
            _viewState.update {
                it.copy(
                    canNext = canNext,
                    canPrevious = canPrevious,
                    currentName = currentBean.name
                )
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