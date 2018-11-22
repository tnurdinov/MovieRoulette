package com.tnurdinov.movieroulette

import android.app.Application

class MovieApp: Application() {
    override fun onCreate() {
        super.onCreate()
        HttpClientHolder.createOkHttpClient(this)
    }
}