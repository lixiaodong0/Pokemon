package com.lixd.pokemon

import android.app.Application
import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.CoilUtils
import com.lixd.pokemon.network.retrofit.RetrofitProvider
import okhttp3.OkHttpClient
import java.io.File

class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        initCoil(this)
    }

    private fun initCoil(context: Context) {
        val imageCacheDir = File(context.getExternalFilesDir("")!!, "image_cache")
        if (!imageCacheDir.exists()) {
            imageCacheDir.mkdirs()
        }
        val imageLoader = ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.02)
                    .directory(imageCacheDir)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .okHttpClient(RetrofitProvider.okHttpClient)
            .respectCacheHeaders(false)
            .build()
        Coil.setImageLoader(imageLoader)
    }

    companion object {
        lateinit var appContext: Context
    }
}