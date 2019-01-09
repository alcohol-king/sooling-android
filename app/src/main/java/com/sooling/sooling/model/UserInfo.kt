package com.sooling.sooling.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
        @SerializedName("drink_cards")
        val drinkCards: List<DrinkCard>,
        @SerializedName("profile_image_url")
        val imgUrl: String,
        @SerializedName("status_message")
        val status_message: String,
        @SerializedName("user_name")
        val userName: String
)