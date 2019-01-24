package com.sooling.sooling.network

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor(private val authToken: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        var builder: Request.Builder = original.newBuilder()
                .header("Authorization", "Bearer "+authToken)

        val request = builder.build()
        return chain.proceed(request)
    }
}