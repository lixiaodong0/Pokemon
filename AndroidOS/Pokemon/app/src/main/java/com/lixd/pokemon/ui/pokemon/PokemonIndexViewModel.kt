package com.lixd.pokemon.ui.pokemon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.lixd.pokemon.data.bean.PokemonIndexBean
import com.lixd.pokemon.data.repository.PokemonRepository
import com.lixd.pokemon.events.UpdatePokemonAvatarEvent
import com.lixd.pokemon.network.retrofit.RetrofitProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class PokemonIndexViewModel(
    private val pokemonRepository: PokemonRepository = PokemonRepository(
        RetrofitProvider.pokemonService
    )
) : ViewModel() {
    init {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    //UI状态
    private val _viewState = MutableStateFlow(PokemonIndexState())
    val viewState = _viewState.asStateFlow()

    //一次性事件，例如toast
    private val _viewEvent = Channel<PokemonIndexEvent>(Channel.BUFFERED)
    val viewEvent = _viewEvent.receiveAsFlow()

    fun onAction(action: PokemonIndexAction) {
        when (action) {
            is PokemonIndexAction.UpdateSelectedIndex -> {
                _viewState.update {
                    it.copy(
                        currentIndex = action.index,
                        currentPokemonIndexBean = action.data
                    )
                }
            }

            is PokemonIndexAction.UpdateCount -> {
                _viewState.update { it.copy(totalCount = action.count) }
            }

            is PokemonIndexAction.FirstSelectFinish -> {
                _viewState.update { it.copy(firstSelect = true) }
            }
        }
    }

    @Subscribe
    fun onUpdatePokemonAvatarEvent(event: UpdatePokemonAvatarEvent) {
        Log.d("Events", "[onUpdatePokemonAvatarEvent]:${event}")
        if (event.id == viewState.value.currentPokemonIndexBean?.number && viewState.value.currentPokemonIndexBean?.avatar?.isEmpty() == true) {
            Log.d("Events", "[onUpdatePokemonAvatarEvent]update avatar")
            //更新列表头像
            modificationEvents.value += PagingDataEvents.Edit(event.id, event.avatar)
            //更新选中头像
            if (viewState.value.currentPokemonIndexBean?.number == event.id) {
                val newCurrentPokemonIndexBean =
                    viewState.value.currentPokemonIndexBean?.copy(avatar = event.avatar)
                _viewState.update { it.copy(currentPokemonIndexBean = newCurrentPokemonIndexBean) }
            }
        }
    }

    private val modificationEvents = MutableStateFlow<List<PagingDataEvents>>(mutableListOf())

    val getPokemonIndex: Flow<PagingData<PokemonIndexBean>> by lazy {
        Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 20),
            pagingSourceFactory = {
                PokemonIndexPagerSource(pokemonRepository, onTotalSize = {
                    onAction(PokemonIndexAction.UpdateCount(it))
                })
            })
            .flow.cachedIn(viewModelScope)
            .combine(modificationEvents) { pagingData, modifications ->
                modifications.fold(pagingData) { acc, event ->
                    applyEvents(acc, event)
                }
            }
    }

    //如何动态修改Paging3列表
    //https://sourcediving.com/crud-operations-with-the-new-android-paging-v3-5bf55110aa4d
    private fun applyEvents(
        paging: PagingData<PokemonIndexBean>,
        events: PagingDataEvents
    ): PagingData<PokemonIndexBean> {
        if (events is PagingDataEvents.Edit) {
            return paging.map {
                if (it.number == events.id) {
                    return@map it.copy(avatar = events.avatar)
                } else {
                    return@map it
                }
            }
        }
        return paging
    }


    sealed class PagingDataEvents {
        class Edit(val id: Int, val avatar: String) : PagingDataEvents()
    }

}