package com.sooling.sooling.service

import com.sooling.sooling.`object`.History
import com.sooling.sooling.network.APIUtiles
import com.sooling.sooling.network.HistoryAPIService
import com.sooling.sooling.util.UserDataManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class HistoryService {

    private var mService: HistoryAPIService? = APIUtiles.getHistoryService(UserDataManager.getToken())

    fun resisterUser(history: History): Observable<Response<History>> {
        return mService!!.createHistory(history).subscribeOn(Schedulers.io())
                .doOnNext({ res ->
                    if (res.code() === HttpsURLConnection.HTTP_OK) {
                        res.body()
                    }
                })
    }


    companion object {
        val instance = HistoryService()
    }

}