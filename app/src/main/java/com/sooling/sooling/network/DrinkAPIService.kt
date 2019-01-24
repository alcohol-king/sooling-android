package com.sooling.sooling.network

import com.sooling.sooling.`object`.Drink
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DrinkAPIService{

    @GET("drinks")
    fun getDrinkList(): Observable<Response<List<Drink>>>

    @GET("drinks/{drinkId}")
    fun getDrink(@Path("drinkId") drinkId : Long): Observable<List<Drink>>

}