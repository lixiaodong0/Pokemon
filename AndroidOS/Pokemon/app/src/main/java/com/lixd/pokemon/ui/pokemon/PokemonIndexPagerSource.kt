package com.lixd.pokemon.ui.pokemon

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lixd.pokemon.data.bean.PokemonIndexBean
import com.lixd.pokemon.data.repository.PokemonRepository
import com.lixd.pokemon.network.service.POKEMON_INDEX_URL

data class CustomKey(val current: Int, val url: String)
class PokemonIndexPagerSource(
    private val pokemonRepository: PokemonRepository,
    private val onTotalSize: (Int) -> Unit = {}
) :
    PagingSource<CustomKey, PokemonIndexBean>() {
    override fun getRefreshKey(state: PagingState<CustomKey, PokemonIndexBean>): CustomKey? = null

    override suspend fun load(params: LoadParams<CustomKey>): LoadResult<CustomKey, PokemonIndexBean> {
        return try {
            val current =
                params.key ?: CustomKey(1, POKEMON_INDEX_URL)      //当前页面 如果为null 返回1 证明是第一页
            val pageSize = params.loadSize   //每页的数据条数

            val result = pokemonRepository.getPokemonList(current.url)
            onTotalSize(result.count)

            val data = mutableListOf<PokemonIndexBean>()
            if (!result.results.isNullOrEmpty()) {
                result.results.forEachIndexed { index, resourceBean ->
                    val number = (current.current - 1) * pageSize + (index + 1)
                    data.add(PokemonIndexBean(number, resourceBean.name, ""))
                }
            }
            val prevKey =
                if (!result.previous.isNullOrEmpty()) CustomKey(
                    current.current - 1,
                    result.previous
                ) else null  //计算上一页，如果返回null,证明到顶
            val nextKey =
                if (!result.next.isNullOrEmpty()) CustomKey(
                    current.current + 1,
                    result.next
                ) else null //计算下一页，如果返回null,证明无数据了
//            Log.e(
//                "lixd",
//                "data:${data.size},current:${current},prevKey:${prevKey},nextKey:${nextKey}"
//            )
            LoadResult.Page(data, prevKey, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}