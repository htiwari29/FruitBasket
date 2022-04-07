package com.fruitbasket.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
 import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fruitbasket.R

@Suppress("DEPRECATION")
class DashboardActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this@DashboardActivity,
                R.drawable.app_gradient_color_background
            )
        )

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_orders,
                R.id.navigation_products,
                R.id.navigation_carts
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        doubleBackToExit()
    }
}