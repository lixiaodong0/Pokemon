package com.lixd.pokemon.network.service

import com.lixd.pokemon.data.bean.PokemonBean
import com.lixd.pokemon.data.bean.ResourceListBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

const val BASE_URL = "https://pokeapi.co/"
const val POKEMON_INDEX_URL = "${BASE_URL}api/v2/pokemon"

interface PokemonService {

    /**
     * 获取宝可梦列表
     * @param url 请求的地址
     */
    @GET
    suspend fun getPokemonList(@Url url: String): ResourceListBean

    /**
     * 获取指定的宝可梦详情数据
     * @param id 宝可梦ID
     */
    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonBean
}