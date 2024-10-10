package com.lixd.pokemon.network.interceptor.cache

import com.lixd.pokemon.PokemonApplication
import com.lixd.pokemon.util.md5
import java.io.File


val REQUEST_CACHE_DIR = File(PokemonApplication.appContext.externalCacheDir!!, "response_cache")

class CacheManager(private val cacheDir: File = REQUEST_CACHE_DIR) {

    private fun getCacheFile(key: String): File {
        val cacheKey = key.md5()
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        return File(cacheDir, cacheKey)
    }

    fun checkCache(key: String): Boolean {
        val cacheFile = getCacheFile(key)
        if (cacheFile.exists()) {
            return true
        }
        return false
    }

    fun readCache(key: String): String? {
        if (checkCache(key)) {
            val file = getCacheFile(key)
            return file.readText()
        }
        return null
    }

    fun writeCache(key: String, content: String): Boolean {
        val file = getCacheFile(key)
        try {
            file.writeText(content)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}