package com.dicoding.snapp_smartnutritionapp.data.response

data class RegisterResponse(
	val error: String,
	val message: String,
	val statusCode: Int
)
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

