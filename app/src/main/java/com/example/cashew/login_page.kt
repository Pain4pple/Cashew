package com.example.cashew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class login_page : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        //Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()




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


    // Check if user is signed in (non-null) and update UI accordingly.
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
    }


}