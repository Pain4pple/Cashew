package com.example.cashew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.cashew.models.user_model
import com.example.cashew.objects.User

class products_page : AppCompatActivity() {
//var currentUser = User.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        //DECLARATIONS
        val cashewBtn: ImageButton = findViewById(R.id.favoritebutton)
        val userName: TextView = findViewById(R.id.uNameDisplay)
        val cashewCoins: TextView = findViewById(R.id.cashewCoinsDisplay)
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)


    if (sh.getString("ID","") != null) {
            userName.text = ""+sh.getString("Username", "")
            cashewCoins.text = ""+sh.getInt("Coins", 0)
        }
        else{
            userName.text = "who you?"
            cashewCoins.text = "you have no money"
        }

        cashewBtn.setOnClickListener {
            val intent = Intent(this, dressup_page::class.java)
            startActivity(intent)
        }
    }


}