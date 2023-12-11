package com.example.cashew

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class profile_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        // DECLARE VARIABLES
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
        val cashewBtn : ImageButton = findViewById(R.id.favoritebutton)
        val productsBtn : ImageButton = findViewById(R.id.productsBtn)
        val profileBtn : ImageButton = findViewById(R.id.profileBtn)

        val orderHistoryBtn : Button = findViewById(R.id.orderHistoryBtn)
        val changePasswordBtn : Button = findViewById(R.id.changePasswordBtn)
        val logOutBtn : Button = findViewById(R.id.logOutBtn)

        cashewBtn.setOnClickListener {
            val intent = Intent(this, dressup_page::class.java)
            startActivity(intent)
        }

        productsBtn.setOnClickListener {
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
        }


        profileBtn.setOnClickListener {
            val intent = Intent(this, profile_page::class.java)
            startActivity(intent)
        }

        orderHistoryBtn.setOnClickListener {
            val intent = Intent(this, dressup_page::class.java)
            startActivity(intent)
        }

        changePasswordBtn.setOnClickListener {
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
        }


        logOutBtn.setOnClickListener {
            val intent = Intent(this, login_page::class.java)
            sh.edit().clear().apply()
            startActivity(intent)
        }

    }
}