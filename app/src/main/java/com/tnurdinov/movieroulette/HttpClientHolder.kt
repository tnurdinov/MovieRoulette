package com.tnurdinov.movieroulette

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient

object HttpClientHolder {
    lateinit var client : OkHttpClient

    fun createOkHttpClient(context: Context) {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(context.cacheDir, cacheSize.toLong())

        client = OkHttpClient.Builder()
                .cache(cache)
                .build()
    }
}