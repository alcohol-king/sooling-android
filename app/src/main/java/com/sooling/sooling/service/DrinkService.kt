package com.sooling.sooling.service

import com.sooling.sooling.`object`.Drink
import com.sooling.sooling.network.APIUtiles
import com.sooling.sooling.network.DrinkAPIService
import com.sooling.sooling.util.UserDataManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class DrinkService {

    private var mService: DrinkAPIService? = APIUtiles.getDrinkService(UserDataManager.getToken())

    fun getDrinkList(): Observable<Response<List<Drink>>> {
        return mService!!.getDrinkList().subscribeOn(Schedulers.io())
                .doOnNext({ res ->
                    if (res.code() === HttpsURLConnection.HTTP_OK) {
                        res.body()
                    }
                })
    }

    companion object {
        val instance = DrinkService()
    }

}