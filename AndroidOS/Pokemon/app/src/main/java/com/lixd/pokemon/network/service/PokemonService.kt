package com.lixd.pokemon.network.service

import com.lixd.pokemon.data.bean.PokemonBean
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    /**
     * 获取指定的宝可梦详情数据
     * @param id 宝可梦ID
     */
    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: String): PokemonBean
}