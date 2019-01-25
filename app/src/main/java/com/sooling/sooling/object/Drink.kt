package com.sooling.sooling.`object`

import com.google.gson.annotations.SerializedName

data class Drink(
        @SerializedName("description")
        val description: String,
        @SerializedName("drink_id")
        val drinkId: Int,
        @SerializedName("drink_type")
        val drinkType: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("number_of_like")
        var likeNum: Int,
        @SerializedName("price")
        val price: Int,
        @SerializedName("proof")
        val proof: Float,
        @SerializedName("drink_img")
        val drinkImg: Int

)