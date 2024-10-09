package com.lixd.pokemon.network.interceptor.cache

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * 缓存拦截器
 */
class CacheInterceptor(
    private val cacheManager: CacheManager
) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val result = chain.request()
        //只针对GET缓存
        if (result.method == "GET") {
            val url = result.url.toString()
            //读取缓存
            if (cacheManager.checkCache(url)) {
                val cacheContent = cacheManager.readCache(url)
                if (!cacheContent.isNullOrEmpty()) {
                    Log.d("CacheInterceptor", "url:${url},读取缓存")
                    return Response.Builder()
                        .code(200)
                        .request(result.newBuilder().build())
                        .body(cacheContent.toResponseBody())
                        .message("from disk cache")
                        .protocol(Protocol.HTTP_2)
                        .build()
                }
            }

            //写入缓存
            val response = chain.proceed(result)
            val responseBody = response.body ?: return response
            val data = responseBody.bytes()
            val contentStr = String(data)
            if (response.code == 200 && contentStr.isNotEmpty()) {
                Log.d("CacheInterceptor", "url:${url},写入缓存")
                cacheManager.writeCache(url, contentStr)
            }
            return response.newBuilder()
                .body(contentStr.toResponseBody())
                .build()
        }
        return chain.proceed(result)
    }
}