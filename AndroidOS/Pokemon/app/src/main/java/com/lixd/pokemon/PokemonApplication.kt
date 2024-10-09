package com.lixd.pokemon

import android.app.Application
import android.content.Context

class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        lateinit var appContext: Context
    }
}