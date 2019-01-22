package com.sooling.sooling.network


object APIUtiles {

    private val BASE_URL = "https://yellowcard-api.herokuapp.com/"

    val getSignInService: SignInAPIService
        get() = RetrofitClient.getClient(BASE_URL).create(SignInAPIService::class.java)

}
