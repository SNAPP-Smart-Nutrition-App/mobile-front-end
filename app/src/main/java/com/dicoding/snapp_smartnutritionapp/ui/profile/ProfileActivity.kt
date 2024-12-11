package com.dicoding.snapp_smartnutritionapp.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.snapp_smartnutritionapp.R
import com.dicoding.snapp_smartnutritionapp.databinding.ActivityMainBinding
import com.dicoding.snapp_smartnutritionapp.databinding.ActivityProfileBinding
import com.dicoding.snapp_smartnutritionapp.ui.editProfile.EditProfileActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi binding
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menampilkan back arrow di action bar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Profil"
        }

        // Menambahkan onClick listener untuk tombol edit profile
        binding.editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}