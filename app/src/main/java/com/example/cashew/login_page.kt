package com.example.cashew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class login_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        private lateinit var auth: FirebaseAuth;
        // ...
        // Initialize Firebase Auth
        auth = Firebase.auth

        // DECLARATIONS
        val signupBtn : Button = findViewById(R.id.signupBtn)
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