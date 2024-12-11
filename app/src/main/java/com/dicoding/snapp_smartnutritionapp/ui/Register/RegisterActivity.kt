package com.dicoding.snapp_smartnutritionapp.ui.Register

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.snapp_smartnutritionapp.data.response.RegisterRequest
import com.dicoding.snapp_smartnutritionapp.data.response.RegisterResponse
import com.dicoding.snapp_smartnutritionapp.data.retrofit.ApiConfig
import com.dicoding.snapp_smartnutritionapp.data.retrofit.ApiService
import com.dicoding.snapp_smartnutritionapp.databinding.ActivityRegisterBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var binding: ActivityRegisterBinding


    companion object{
        private const val TAG = "MainViewModel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        apiService = ApiConfig.getApiService()

        binding.SignUp.setOnClickListener {
            val username = binding.editInputUsername.text.toString()
            val email = binding.editEmail.text.toString()
            val password = binding.editInputPassword.text.toString()

            if (validateInput(username, email, password)) {
                registerUser(username, email, password)
            }
        }
    }

    fun registerUser(username: String, email: String, password: String) {
        showLoading(true)
        val request = RegisterRequest(username, email, password)
        val client = ApiConfig.getApiService().register(request)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, "Registration Success: ${it.message}")
                        Snackbar.make(binding.root, "Registrasi berhasil!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.GREEN)
                            .setTextColor(Color.WHITE)
                            .show()
                    }
                } else {
                    val errorBody = Gson().fromJson(response.errorBody()?.string(), RegisterResponse::class.java)
                    Log.e(TAG, "Registration Failed: ${errorBody.message}")
                    Snackbar.make(binding.root, "Registrasi gagal: ${errorBody.message}", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
                Snackbar.make(binding.root, "Terjadi kesalahan: ${t.message}", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#F44336"))
                    .setTextColor(Color.WHITE)
                    .show()
            }
        })
    }
    // Fungsi untuk validasi input
    private fun validateInput(username: String, email: String, password: String): Boolean {
        if (username.isEmpty()) {
            binding.editInputUsername.error = "Username harus diisi"
            return false
        }
        if (email.isEmpty()) {
            binding.editEmail.error = "Email harus diisi"
            return false
        }
        if (password.isEmpty()) {
            binding.editInputPassword.error = "Password harus diisi"
            return false
        }
        return true
    }
    // Fungsi untuk menampilkan atau menyembunyikan dialog loading
    private fun showLoading(isLoading: Boolean) {
    }

}