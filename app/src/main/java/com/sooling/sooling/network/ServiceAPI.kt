package com.sooling.sooling.network

import com.sooling.sooling.model.Drink
import com.sooling.sooling.model.UserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceAPI {
    @GET("/main")
    fun getMainInfo(): UserInfo

    @POST("/drinks")
    fun createDrink(@Body drink: Drink)
}