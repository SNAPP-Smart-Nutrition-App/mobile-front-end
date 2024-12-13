package com.dicoding.snapp_smartnutritionapp.data.response

import com.google.gson.annotations.SerializedName

data class EditProfileResponse(

	@field:SerializedName("message")
	val message: String
)
data class EditProfileRequest(
	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("password")
	val password: String
)
