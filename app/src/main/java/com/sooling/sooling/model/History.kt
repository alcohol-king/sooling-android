package com.sooling.sooling.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class History(
        @SerializedName("drunk_at")
        val drunkAt: LocalDate,
        @SerializedName("beer")
        val beer: Int,
        @SerializedName("soju")
        val soju: Int,
        @SerializedName("makgeolli")
        val makgeolli: Int,
        @SerializedName("wine")
        val wine: Int,
        @SerializedName("user_id")
        val userID: Int,
        @SerializedName("history_id")
        val historyID: Int
)