package com.morse.movie.app

import android.annotation.SuppressLint
import android.app.Application
import com.morse.movie.local.LocalDataStore

@SuppressLint("StaticFieldLeak")
lateinit var instance: LocalDataStore

class MaoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = LocalDataStore(this)
    }

}