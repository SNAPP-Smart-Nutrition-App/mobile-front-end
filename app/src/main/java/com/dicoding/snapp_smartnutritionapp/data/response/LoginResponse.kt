package com.dicoding.snapp_smartnutritionapp.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	val message: String,
	val token: String,
	val username: String
)
data class LoginRequest(
	@field:SerializedName("email")
	val email: String,
	@field:SerializedName("password")
	val password: String
)
