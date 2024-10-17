package com.lixd.pokemon.ui.pokemon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.insertFooterItem
import androidx.paging.map
import com.lixd.pokemon.data.bean.PokemonIndexBean
import com.lixd.pokemon.data.repository.PokemonRepository
import com.lixd.pokemon.events.UpdatePokemonAvatarEvent
import com.lixd.pokemon.network.retrofit.RetrofitProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import okhttp3.CertificatePinner
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

    val viewStatus = MutableStateFlow(PokemonIndexState())

    fun updateSelectedIndex(index: Int, bean: PokemonIndexBean) {
        viewStatus.update { it.copy(currentIndex = index, currentPokemonIndexBean = bean) }
    }

    fun updateTotalCount(totalCount: Int) {
        viewStatus.update { it.copy(totalCount = totalCount) }
    }


    @Subscribe
    fun onUpdatePokemonAvatarEvent(event: UpdatePokemonAvatarEvent) {
        Log.d("Events", "[onUpdatePokemonAvatarEvent]:${event}")
        if (event.id == viewStatus.value.currentPokemonIndexBean?.number && viewStatus.value.currentPokemonIndexBean?.avatar?.isEmpty() == true) {
            Log.d("Events", "[onUpdatePokemonAvatarEvent]update avatar")
            //更新列表头像
            modificationEvents.value += PagingDataEvents.Edit(event.id, event.avatar)
            //更新选中头像
            val newCurrentPokemonIndexBean =
                viewStatus.value.currentPokemonIndexBean?.copy(avatar = event.avatar)
            viewStatus.update { it.copy(currentPokemonIndexBean = newCurrentPokemonIndexBean) }
        }
    }

    private val modificationEvents = MutableStateFlow<List<PagingDataEvents>>(mutableListOf())

    val getPokemonIndex: Flow<PagingData<PokemonIndexBean>> by lazy {
        Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 20),
            pagingSourceFactory = {
                PokemonIndexPagerSource(pokemonRepository, onTotalSize = {
                    updateTotalCount(it)
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