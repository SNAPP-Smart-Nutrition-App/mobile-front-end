package com.dicoding.snapp_smartnutritionapp.data.response.Cobacoba

import android.content.Context

class UserPreference(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUser(username: String, email: String, token: String) {
        preferences.edit().apply {
            putString(USERNAME_KEY, username)
            putString(EMAIL_KEY, email)
            putString(TOKEN_KEY, token)
            apply()
        }
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val USERNAME_KEY = "username"
        private const val EMAIL_KEY = "email"
        private const val TOKEN_KEY = "token"
    }
} 