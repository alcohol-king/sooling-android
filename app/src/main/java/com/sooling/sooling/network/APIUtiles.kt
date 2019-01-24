package com.sooling.sooling.network


object APIUtiles {

    private val BASE_URL = "https://yellowcard-api.herokuapp.com/"

    val getSignInService: SignInAPIService
        get() = RetrofitClient.getClient(BASE_URL).create(SignInAPIService::class.java)

    fun getDrinkService(accessToken: String): DrinkAPIService {
        return RetrofitClient.getClientWithToken(BASE_URL, accessToken)!!.create(DrinkAPIService::class.java)
    }

    fun getHistoryService(accessToken: String): HistoryAPIService {
        return RetrofitClient.getClientWithToken(BASE_URL, accessToken)!!.create(HistoryAPIService::class.java)
    }

}
