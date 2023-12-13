package com.example.cashew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.cashew.models.user_model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class profile_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        // DECLARE VARIABLES
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
        val productButton : ImageButton = findViewById(R.id.productButton3)
        val cartButton : ImageButton = findViewById(R.id.cartButton3)
        val profileBtn : ImageButton = findViewById(R.id.profileBtn3)

        val orderHistoryBtn : Button = findViewById(R.id.orderHistoryBtn)
        val changePasswordBtn : Button = findViewById(R.id.changePasswordBtn)
        val logOutBtn : Button = findViewById(R.id.logOutBtn)

        cartButton.setOnClickListener {
            val intent = Intent(this, cart_page::class.java)
            startActivity(intent)
        }

        productButton.setOnClickListener {
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
        }


        profileBtn.setOnClickListener {
            Toast.makeText(this,"You are already here!",Toast.LENGTH_SHORT).show()
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