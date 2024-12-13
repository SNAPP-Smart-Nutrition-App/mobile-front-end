package com.dicoding.snapp_smartnutritionapp.ui.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.snapp_smartnutritionapp.data.response.EditProfileRequest
import com.dicoding.snapp_smartnutritionapp.data.response.EditProfileResponse
import com.dicoding.snapp_smartnutritionapp.data.retrofit.ApiConfig
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileViewModel:ViewModel() {
    private val _EditProfileResult = MutableLiveData<Result<EditProfileResponse>>()
    val loginResult: LiveData<Result<EditProfileResponse>> = _EditProfileResult

    fun editProfileUser(username: String, password: String) {
        val request = EditProfileRequest(username, password)
        val client = ApiConfig.getApiService().editProfile(request)
        client.enqueue(object : Callback<EditProfileResponse> {
            override fun onResponse(
                call: Call<EditProfileResponse>,
                response: Response<EditProfileResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _EditProfileResult.value = Result.success(it)
                    }
                } else {
                    val errorBody = Gson().fromJson(response.errorBody()?.string(), EditProfileResponse::class.java)
                    _EditProfileResult.value = Result.failure(Exception(errorBody.message))
                }

            }
            override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                _EditProfileResult.value = Result.failure(t)
            }
        })
    }
}