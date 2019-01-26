package com.sooling.sooling.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.sooling.sooling.`object`.User


object UserDataManager {
    private val sharedPrefName = "mannaja_sharedpref"
    private val keyToken = "key_token"
    private val keyName = "key_name"
    private val keyImgUrl = "key_img"
    private val keyMsg = "key_Msg"
    private val keyLabel = "key_label"
    private lateinit var sharedPreferences: SharedPreferences

    @Synchronized
    fun getInstance(context: Context): UserDataManager {
        sharedPreferences = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        return UserDataManager
    }

    fun saveUserInfo(user: User) {
        val editor = sharedPreferences.edit()
        editor.putString(keyToken, user.token)
        editor.putString(keyName, user.name)
        editor.putString(keyImgUrl, user.imgUrl)
        editor.putString(keyMsg, user.msg)

        editor.apply()
    }

    fun saveUserName(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString(keyName, name)

        editor.apply()
    }

    fun saveUserMsg(msg: String) {
        val editor = sharedPreferences.edit()
        editor.putString(keyMsg, msg)

        editor.apply()
    }

    fun saveUserImg(img: String) {
        val editor = sharedPreferences.edit()
        editor.putString(keyImgUrl, img)

        editor.apply()
    }

    fun saveMemberLabel(labels: ArrayList<String>) {
        val editor = sharedPreferences.edit()

        val json = Gson().toJson(labels)
        editor.putString(keyLabel, json)
        editor.apply()
    }

    fun getUserInfo(): User = User(
            sharedPreferences.getString(keyToken, "none_key"),
            sharedPreferences.getString(keyName, "none_name"),
            sharedPreferences.getString(keyImgUrl, "none_img"),
            sharedPreferences.getString(keyMsg, "")
    )

    fun getToken() = sharedPreferences.getString(keyToken, "none_key")

    fun getLabels() = sharedPreferences.getStringSet(keyLabel, null)
}