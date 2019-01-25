package com.sooling.sooling.network

import com.sooling.sooling.`object`.History
import com.sooling.sooling.`object`.UserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserInfoServiceAPI {
    @GET("main")
    fun getMainInfo(): UserInfo

    @POST("histories")
    fun createHistory(@Body history: History)

    @GET("history")
    fun getHistory()
}