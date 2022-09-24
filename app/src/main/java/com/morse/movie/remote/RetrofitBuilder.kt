package com.morse.movie.remote

import android.os.Environment
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit


object RetrofitBuilder {

    private var userRetrofit: Retrofit? = null

    private fun getInstance(): Retrofit {
        return userRetrofit ?: createRetrofit().also { userRetrofit = it }
    }

    fun getMoviesAPI() = getInstance().create(MoviesApis::class.java)

    private fun createRetrofit(): Retrofit {
        val gson = GsonBuilder().disableHtmlEscaping()
        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").apply {
            addConverterFactory(GsonConverterFactory.create(gson.create()))
            addCallAdapterFactory(CoroutineCallAdapterFactory())

            client(getClientOkHttpInstance())
        }.build()
    }

    private fun getCache(): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(
            Environment.getDownloadCacheDirectory(), cacheSize.toLong()
        )
    }

    private fun getClientOkHttpInstance(): OkHttpClient {
        val okHttp = OkHttpClient.Builder()
            .cache(getCache())
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
        return okHttp.build()
    }

}

