package com.dicoding.snapp_smartnutritionapp.data.response.Cobacoba

object DummmyData {
    val dummyUsers = listOf(
        User("user1", "user1@gmail.com", "password1"),
        User("user2", "user2@gmail.com", "password2"),
        User("admin", "admin@gmail.com", "admin123")
    )

    data class User(
        val username: String,
        val email: String,
        val password: String
    )
}