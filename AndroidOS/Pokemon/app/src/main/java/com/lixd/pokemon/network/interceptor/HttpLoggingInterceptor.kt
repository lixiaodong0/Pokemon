package com.lixd.pokemon.network.interceptor

import okhttp3.logging.HttpLoggingInterceptor

val httpLoggingInterceptor by lazy {
    HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}