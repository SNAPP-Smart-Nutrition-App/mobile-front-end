package com.dicoding.snapp_smartnutritionapp.ui.editProfile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.snapp_smartnutritionapp.R
import com.dicoding.snapp_smartnutritionapp.databinding.ActivityEditProfileBinding
import com.dicoding.snapp_smartnutritionapp.databinding.ActivityLoginactivityBinding
import com.dicoding.snapp_smartnutritionapp.ui.Login.LoginViewModel

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val editViewModel: EditProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Inisialisasi binding
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Gunakan binding.root sebagai content view

        // Menampilkan back arrow di action bar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Edit Profil"
        }
        binding.btnSave.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            editViewModel.editProfileUser(email, password)
        }

        editViewModel.loginResult.observe(this) { result ->
            result.onSuccess { response ->
                // Tampilkan pesan sukses
                Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish()
            }.onFailure { exception ->
                // Tampilkan pesan error
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}