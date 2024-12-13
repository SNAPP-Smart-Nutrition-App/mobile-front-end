package com.dicoding.snapp_smartnutritionapp.data.local

import android.content.Context

class SessionManager {
    
}

class UserPreference(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUserData(email: String, name: String, token: String) {
        val editor = preferences.edit()
        editor.putString(EMAIL, email)
        editor.putString(NAME, name)
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getEmail(): String? = preferences.getString(EMAIL, null)
    fun getName(): String? = preferences.getString(NAME, null)
    fun getToken(): String? = preferences.getString(TOKEN, null)

    fun clearUserData() {
        preferences.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val EMAIL = "email"
        private const val NAME = "name"
        private const val TOKEN = "token"
    }
}