package com.dicoding.snapp_smartnutritionapp.data.retrofit


import com.dicoding.snapp_smartnutritionapp.data.response.Cobacoba.EventResponse
import com.dicoding.snapp_smartnutritionapp.data.response.LoginRequest
import com.dicoding.snapp_smartnutritionapp.data.response.RegisterRequest
import com.dicoding.snapp_smartnutritionapp.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET


import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
    @POST("login")
    fun login(@Body request: LoginRequest): Call<RegisterResponse>

//    Test Dummy
@GET("events")
    fun getEvents(
        @Query("active") active: Int = 0, // Menampilkan semua event
        @Query("q") query: String? = null,
        @Query("limit") limit: Int = 40 // default 40
    ): Call<EventResponse>
}