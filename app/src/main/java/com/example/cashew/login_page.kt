package com.example.cashew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class login_page : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)



        // DECLARATIONS
        val signupBtn : Button = findViewById(R.id.backLogInBtn)
        val loginBtn : Button = findViewById(R.id.loginBtn)
        var username : EditText = findViewById(R.id.usernameEditText)
        var password : EditText = findViewById(R.id.passwordEditText)





        loginBtn.setOnClickListener{
            val intent = Intent(this, orderways_page::class.java)
            startActivity(intent)
        }

        signupBtn.setOnClickListener{
            val intent = Intent(this, signup_page::class.java)
            startActivity(intent)
        }
    }




}