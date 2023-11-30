package com.example.cashew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.cashew.models.user_model
import com.example.cashew.objects.User

class products_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        //DECLARATIONS
        val cashewBtn: ImageButton = findViewById(R.id.cashewlogoBtn)
        val userName: TextView = findViewById(R.id.uNameDisplay)
        val cashewCoins: TextView = findViewById(R.id.cashewCoinsDisplay)
        val user = User()
        var uName = intent.extras?.getString("userUname")
        var userData: user_model? = user.getUserData(uName.toString())


        Toast.makeText(this, "What would you like to do, $uName?", Toast.LENGTH_SHORT).show()
        if (userData != null) {
            userName.text = ""+userData.userUname
            cashewCoins.text = ""+userData.userCashewCoins
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