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
import com.dicoding.snapp_smartnutritionapp.data.response.Cobacoba.DummmyData
import com.dicoding.snapp_smartnutritionapp.data.response.Cobacoba.UserPreference
import com.dicoding.snapp_smartnutritionapp.ui.LoadingDialog

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.tvLogin.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            
            performDummyLogin(email, password)
        }

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

    private fun performDummyLogin(email: String, password: String) {
        // Simulasi loading
       showLoading(true)

        // Simulasi delay network
        Handler(Looper.getMainLooper()).postDelayed({
           showLoading(false)
            
            // Cek kredensial dengan data dummy
            val user = DummmyData.dummyUsers.find {
                it.email == email && it.password == password 
            }

            if (user != null) {
                // Simpan data user ke SharedPreferences
                val userPref = UserPreference(this)
                userPref.saveUser(
                    username = user.username,
                    email = user.email,
                    token = "dummy-token-${user.username}"
                )

                // Tampilkan pesan sukses
                Snackbar.make(binding.root, "Login berhasil!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.GREEN)
                    .setTextColor(Color.WHITE)
                    .show()

                // Pindah ke HomeActivity
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                // Tampilkan pesan error
                Snackbar.make(binding.root, "Email atau password salah!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.RED)
                    .setTextColor(Color.WHITE)
                    .show()
            }
        }, 2000) // Delay 1 detik untuk simulasi network
    }
    
}