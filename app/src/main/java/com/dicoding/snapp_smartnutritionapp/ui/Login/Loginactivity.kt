package com.dicoding.snapp_smartnutritionapp.ui.Login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.snapp_smartnutritionapp.MainActivity
import com.dicoding.snapp_smartnutritionapp.R
import com.dicoding.snapp_smartnutritionapp.databinding.ActivityLoginactivityBinding
import com.dicoding.snapp_smartnutritionapp.databinding.ActivityMainBinding
import com.dicoding.snapp_smartnutritionapp.ui.Navigation.HomeActivity
import com.dicoding.snapp_smartnutritionapp.ui.Register.RegisterActivity
import com.google.android.material.snackbar.Snackbar
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.dicoding.snapp_smartnutritionapp.data.response.LoginRequest
import com.dicoding.snapp_smartnutritionapp.data.response.LoginResponse
import com.dicoding.snapp_smartnutritionapp.data.response.RegisterResponse
import com.dicoding.snapp_smartnutritionapp.data.retrofit.ApiConfig
import com.dicoding.snapp_smartnutritionapp.ui.LoadingDialog
import com.dicoding.snapp_smartnutritionapp.ui.Navigation.ui.home.HomeFragment
import com.dicoding.snapp_smartnutritionapp.ui.Navigation.ui.home.HomeViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.dicoding.snapp_smartnutritionapp.data.local.UserPreference

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginactivityBinding
    private lateinit var userPreference: UserPreference
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        userPreference = UserPreference(this)

        binding.tvLogin.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            loginViewModel.loginUser(email, password)
        }

        loginViewModel.loginResult.observe(this, Observer { result ->
            showLoading(false)
            result.onSuccess { loginResponse ->
                // Sesuaikan dengan struktur LoginResponse dari API

                // Log untuk debugging
                // Langsung pindah ke HomeActivity
                val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    // Kirim pesan sukses sebagai extra
                    putExtra("showSuccessMessage", true)
                    putExtra("openHomeFragment", true)
                }
                startActivity(intent)
                finish()
            }.onFailure { exception ->
                Log.e(TAG, "Login Failed: ${exception.message}")
                Snackbar.make(binding.root, "Login gagal: ${exception.message}", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.RED)
                    .setTextColor(Color.WHITE)
                    .show()
            }
        })

        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)
        tvSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            LoadingDialog.show(this)
        } else {
            LoadingDialog.hide()
        }
    }

    companion object{
        private const val TAG = "Login Activiry"
    }
}