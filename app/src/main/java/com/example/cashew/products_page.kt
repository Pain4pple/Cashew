package com.example.cashew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast

class products_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        //DECLARATIONS
        val cashewBtn: ImageButton = findViewById(R.id.cashewlogoBtn)

        cashewBtn.setOnClickListener {
            val intent = Intent(this, dressup_page::class.java)
            startActivity(intent)
        }
    }


}