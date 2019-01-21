package com.sooling.sooling.model

import android.content.Context
import com.sooling.sooling.R

class GetCardData(val context: Context) {
    val beer = "BEER"
    val soju = "SOJU"
    val makgeolli = "MAKGEOLLI"
    val wine = "WINE"

    fun getColor(type: String): Int =
            when (type) {
                beer -> R.color.colorBeer
                wine -> R.color.colorWine
                soju -> R.color.colorSoju
                makgeolli -> R.color.colorMakgeolli
                else -> R.color.colorGray
            }

    fun getFirstMsg(type: String): String =
            context.getString(R.string.main_msg1) + " " +
                    when (type) {
                        beer -> context.getString(R.string.all_beer)
                        wine -> context.getString(R.string.all_wine)
                        soju -> context.getString(R.string.all_soju)
                        makgeolli -> context.getString(R.string.all_makgeolli)
                        else -> context.getString(R.string.main_msg4)
                    }

    fun getLastMsg(type: String): String =
            when (type) {
                beer, wine, soju, makgeolli ->
                    context.getString(R.string.main_msg3)
                else -> context.getString(R.string.main_msg4)
            }

    fun getDrinkIcon(type: String): Int =
            when (type) {
                beer -> R.drawable.img_beer
                else -> R.drawable.img_beer
            }

    fun getCapacityArray(type: String): Array<String> = context.resources.getStringArray(
            when (type) {
                "맥주" -> R.array.beer_capacity
                "와인" -> R.array.wine_capacity
                "소주" -> R.array.soju_capacity
                "막걸리" -> R.array.makeolli_capacity
                else -> R.array.beer_capacity
            })
}