package ru.hotmule.vkfeed.utils

import android.content.Context

class TokenPreference(context: Context) {

    private val PREFERENCE_NAME = "token_shared_preference"
    private val ACCESS_TOKEN_KEY = "access_token"
    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getAccessToken() = preference.getString(ACCESS_TOKEN_KEY, null)

    fun setAccessToken(token: String) {
        val editor = preference.edit()
        editor.putString(ACCESS_TOKEN_KEY, token)
        editor.apply()
    }
}