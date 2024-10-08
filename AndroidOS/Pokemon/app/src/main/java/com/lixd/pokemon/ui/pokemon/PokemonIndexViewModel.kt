package com.lixd.pokemon.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lixd.pokemon.data.bean.PokemonIndexBean
import com.lixd.pokemon.data.repository.PokemonRepository
import com.lixd.pokemon.network.retrofit.RetrofitProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class PokemonIndexViewModel(
    private val pokemonRepository: PokemonRepository = PokemonRepository(
        RetrofitProvider.pokemonService
    )
) : ViewModel() {

    val viewStatus = MutableStateFlow(PokemonIndexState())

    fun updateSelectedIndex(index: Int, bean: PokemonIndexBean) {
        viewStatus.update { it.copy(currentIndex = index, currentPokemonIndexBean = bean) }
    }

    fun updateTotalCount(totalCount: Int) {
        viewStatus.update { it.copy(totalCount = totalCount) }
    }

    val getPokemonIndex: Flow<PagingData<PokemonIndexBean>> by lazy {
        Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 20),
            pagingSourceFactory = {
                PokemonIndexPagerSource(pokemonRepository, onTotalSize = {
                    updateTotalCount(it)
                })
            })
            .flow.cachedIn(viewModelScope)
    }

}