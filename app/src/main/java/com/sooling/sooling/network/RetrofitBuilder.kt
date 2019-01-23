package com.sooling.sooling.network

import com.sooling.sooling.service.SignInService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    val publicUrl = "https://yellowcard-api.herokuapp.com/"
    val restAPI: SignInAPIService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(publicUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        restAPI = retrofit.create(SignInAPIService::class.java)
    }
}