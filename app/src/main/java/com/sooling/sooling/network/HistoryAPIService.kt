package com.sooling.sooling.network

import com.sooling.sooling.`object`.History
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface HistoryAPIService {

    @POST
    fun createHistory (@Body history:History): Observable<Response<History>>

}