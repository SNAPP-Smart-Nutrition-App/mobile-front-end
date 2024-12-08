package com.dicoding.snapp_smartnutritionapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.snapp_smartnutritionapp.databinding.ActivityMainBinding
import com.dicoding.snapp_smartnutritionapp.ui.Login.Loginactivity
import com.dicoding.snapp_smartnutritionapp.ui.Navigation.ui.Scan.ScanFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val tvStarted = findViewById<TextView>(R.id.started)
        tvStarted.setOnClickListener {
            // Comment sementara untuk cobain fitur camera
//            val intent = Intent(this, Loginactivity::class.java)
//            startActivity(intent)
            supportFragmentManager.beginTransaction()
                .replace(R.id.camera_fragment_container, ScanFragment()).addToBackStack(null).commit()
        }
    }
}