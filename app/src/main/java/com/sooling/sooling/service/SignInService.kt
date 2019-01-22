package com.sooling.sooling.service

import com.sooling.sooling.`object`.SignIn
import com.sooling.sooling.network.APIUtiles
import com.sooling.sooling.network.RetrofitClient
import com.sooling.sooling.network.SignInAPIService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class SignInService private constructor() {
    private var mService: SignInAPIService? = null

    private fun SignInService() {
        mService = RetrofitClient.getClient("https://yellowcard-api.herokuapp.com/").create(SignInAPIService::class.java)
    }

    fun resisterUser(signIn: SignIn): Observable<Response<SignIn>> {
        return mService!!.resisterUser(signIn).subscribeOn(Schedulers.io())
                .doOnNext({ res ->
                    if (res.code() === HttpsURLConnection.HTTP_OK) {
                        res.body()
                    }
                })
    }


    companion object {
        val instance = SignInService()
    }
}
