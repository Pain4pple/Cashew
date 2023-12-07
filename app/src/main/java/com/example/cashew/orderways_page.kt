package com.example.cashew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pl.droidsonroids.gif.GifImageView

class orderways_page : AppCompatActivity(){
    private var drawableResource : Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderways)

        val dineInBtn : Button = findViewById(R.id.dineinBtn2)
        val cashewGif : GifImageView = findViewById(R.id.cashew)
        var sp = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
        var uName = sp.getString("Username","")
        val userCashew = sp.getString("Wardrobe", "").toString()

        drawableResource = when (userCashew) {
            "sunnies" -> R.drawable.sunniescashew
            "octo" -> R.drawable.octocashew
            "bow" -> R.drawable.bowcashew
            "mcdo" -> R.drawable.macdo_slave
            "default" -> R.drawable.smileycashew1
            else -> R.drawable.smileycashew1
        }
        cashewGif.setImageResource(drawableResource!!)

        Toast.makeText(this, "What would you like to do, $uName?", Toast.LENGTH_SHORT).show()

        dineInBtn.setOnClickListener{
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
        }
    }
}