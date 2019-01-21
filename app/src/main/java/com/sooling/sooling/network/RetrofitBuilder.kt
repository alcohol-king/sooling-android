package com.sooling.sooling.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    val publicUrl = "http://sooling.com"
    val restAPI: ServiceAPI

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(publicUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        restAPI = retrofit.create(ServiceAPI::class.java)
    }

}