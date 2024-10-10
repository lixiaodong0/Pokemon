package com.lixd.pokemon.network.retrofit

import com.lixd.pokemon.PokemonApplication
import com.lixd.pokemon.network.interceptor.cache.CacheInterceptor
import com.lixd.pokemon.network.interceptor.cache.CacheManager
import com.lixd.pokemon.network.interceptor.httpLoggingInterceptor
import com.lixd.pokemon.network.service.BASE_URL
import com.lixd.pokemon.network.service.PokemonService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {
    val okHttpClient: OkHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(CacheInterceptor(CacheManager()))
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val pokemonService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        retrofit.create(PokemonService::class.java)
    }
}