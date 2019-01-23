package com.sooling.sooling.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    val url = "https://yellowcard-api.herokuapp.com/"

    companion object {
        private val httpClient = OkHttpClient.Builder()

        fun getClient(url: String): Retrofit {
            return Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                    .build()
        }
    }
}