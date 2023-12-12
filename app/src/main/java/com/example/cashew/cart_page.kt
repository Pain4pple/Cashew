package com.example.cashew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class cart_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val cashewBtn: ImageButton = findViewById(R.id.favoritebutton2)
        val productsBtn : ImageButton = findViewById(R.id.productsBtn2)
        val profileBtn : ImageButton = findViewById(R.id.profileBtn2)
        val orderWay: TextView = findViewById(R.id.orderWay)


        cashewBtn.setOnClickListener {
            val intent = Intent(this, dressup_page::class.java)
            startActivity(intent)
        }

        productsBtn.setOnClickListener {
            val intent = Intent(this, cart_page::class.java)
            startActivity(intent)
        }


        profileBtn.setOnClickListener {
            val intent = Intent(this, profile_page::class.java)
            startActivity(intent)
        }

    }
}