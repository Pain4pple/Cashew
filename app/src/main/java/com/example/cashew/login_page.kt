package com.example.cashew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar


class login_page : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        actionBar?.hide()
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

        // SIGN-UP BUTTON TO REDIRECT TO SIGN UP PAGE
        signupBtn.setOnClickListener{
            val intent = Intent(this, signup_page::class.java)
            startActivity(intent)
        }
    }




}