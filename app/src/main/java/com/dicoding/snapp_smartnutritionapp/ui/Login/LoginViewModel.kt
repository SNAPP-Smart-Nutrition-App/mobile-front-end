package com.dicoding.snapp_smartnutritionapp.ui.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.snapp_smartnutritionapp.data.response.LoginRequest
import com.dicoding.snapp_smartnutritionapp.data.response.LoginResponse
import com.dicoding.snapp_smartnutritionapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun loginUser(email: String, password: String) {
        val request = LoginRequest(email, password)
        val client = ApiConfig.getApiService().login("Basic " + android.util.Base64.encodeToString("$email:$password".toByteArray(), android.util.Base64.NO_WRAP), request)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _loginResult.value = Result.success(it)
                    }
                } else {
                    val errorBody = Gson().fromJson(response.errorBody()?.string(), LoginResponse::class.java)
                    _loginResult.value = Result.failure(Exception(errorBody.message))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.value = Result.failure(t)
            }
        })
    }
}