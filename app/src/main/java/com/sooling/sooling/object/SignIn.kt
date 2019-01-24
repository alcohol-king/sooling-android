package com.sooling.sooling.`object`

import com.google.gson.annotations.SerializedName

data class SignIn(

    @SerializedName("access_token")
    var accessToken: String,
    @SerializedName("status_message")
    var statusMessage: String,
    @SerializedName("user_name")
    var userName: String,
    @SerializedName("token")
    var token: String

)