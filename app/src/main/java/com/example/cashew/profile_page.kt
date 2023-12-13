package com.example.cashew

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast

class profile_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        // DECLARE VARIABLES
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
        val cashewBtn: ImageButton = findViewById(R.id.favoritebutton)
        val productsBtn: ImageButton = findViewById(R.id.productsBtn)
        val profileBtn: ImageButton = findViewById(R.id.profileBtn)

        val orderHistoryBtn: Button = findViewById(R.id.orderHistoryBtn)
        val changePasswordBtn: Button = findViewById(R.id.changePasswordBtn)
        val logOutBtn: Button = findViewById(R.id.logOutBtn)

        //val dialog: Dialog = findViewById(R.layout.activity_log_out_dialog)





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
            //val intent = Intent(this, login_page::class.java)
            //sh.edit().clear().apply()
            //startActivity(intent)
            showDialog()
        }

    }
    fun showDialog() {
        val fragmentManager = supportFragmentManager
        val newFragment = logOut_dialog()
        newFragment.show(supportFragmentManager, "logout")

    }
}
