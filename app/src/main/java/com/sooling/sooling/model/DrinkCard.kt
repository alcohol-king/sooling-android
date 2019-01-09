package com.sooling.sooling.model

import com.google.gson.annotations.SerializedName

data class DrinkCard(
        @SerializedName("drink_type")
        val drinkType: String,
        @SerializedName("message")
        val message: String
)