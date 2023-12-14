package com.example.cashew

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView
import android.widget.Toast
import pl.droidsonroids.gif.GifImageView

class profile_page : AppCompatActivity() {
    private var drawableResource : Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        // DECLARE VARIABLES
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
        val userCashew = sh.getString("Wardrobe", "").toString()
        val cashewGif: GifImageView = findViewById(R.id.userCashewWardrobe2)
        val orderHistoryBtn: Button = findViewById(R.id.orderHistoryBtn)
        val user: TextView = findViewById(R.id.user)
        val changePasswordBtn: Button = findViewById(R.id.changePasswordBtn)
        val logOutBtn: Button = findViewById(R.id.logOutBtn)

        val productButton : ImageButton = findViewById(R.id.productButton3)
        val cartButton : ImageButton = findViewById(R.id.cartButton3)
        val profileBtn : ImageButton = findViewById(R.id.profileBtn3)

        drawableResource = when (userCashew) {
            "sunnies" -> R.drawable.sunniescashew
            "octo" -> R.drawable.octocashew
            "bow" -> R.drawable.bowcashew
            "mcdo" -> R.drawable.macdo_slave
            "default" -> R.drawable.cashew1
            else -> R.drawable.cashew1
        }
        cashewGif.setImageResource(drawableResource!!)

        if (sh.getString("ID","") != null) {
            user.text = "Hi, "+sh.getString("Username", "")+"!"
        }
        else{
            user.text = "who you?"
        }
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
