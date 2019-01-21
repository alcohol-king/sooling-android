package com.sooling.sooling.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.google.gson.JsonArray
import com.sooling.sooling.`object`.SignIn
import io.reactivex.Observable
import retrofit2.Call

interface SignUpAPIService{

    @POST("signin")
    fun resisterUser(@Body signIn: SignIn): Observable<Response<SignIn>>

}