package com.example.cashew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class orderways_page : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderways)

        val dineInBtn : Button = findViewById(R.id.dineinBtn2)

        dineInBtn.setOnClickListener{
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
        }
    }
}