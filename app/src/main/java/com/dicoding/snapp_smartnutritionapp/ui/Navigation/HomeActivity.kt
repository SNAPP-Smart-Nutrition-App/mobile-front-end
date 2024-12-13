package com.dicoding.snapp_smartnutritionapp.ui.Navigation

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.snapp_smartnutritionapp.R
import com.dicoding.snapp_smartnutritionapp.databinding.ActivityHomeBinding
import com.dicoding.snapp_smartnutritionapp.ui.Navigation.ui.home.HomeFragment
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (intent.getBooleanExtra("openHomeFragment", false)) {
            navController.navigate(R.id.navigation_home)
            
            // Tampilkan pesan sukses setelah masuk ke HomeFragment
            if (intent.getBooleanExtra("showSuccessMessage", false)) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Login berhasil!",
                    Snackbar.LENGTH_LONG
                )
                    .setBackgroundTint(Color.GREEN)
                    .setTextColor(Color.WHITE)
                    .show()
            }
        }
    }
}