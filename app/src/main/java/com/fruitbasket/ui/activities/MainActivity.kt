package com.fruitbasket.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.fruitbasket.R
import com.fruitbasket.utils.Constants

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences =
            getSharedPreferences(Constants.FRUITBASKET_PREFERENCES, Context.MODE_PRIVATE)

        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")!!
        val tv_main = findViewById<TextView>(R.id.tv_main)
        tv_main.text= "Hello $username"
    }
}