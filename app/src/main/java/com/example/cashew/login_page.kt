package com.example.cashew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashew.models.user_model
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class login_page : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var username: EditText
    private lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // DECLARATIONS
        val signupBtn : Button = findViewById(R.id.backLogInBtn)
        val loginBtn : Button = findViewById(R.id.loginBtn)
        username = findViewById(R.id.usernameEditText)
        password = findViewById(R.id.passwordEditText)

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.reference.child("Users")



        loginBtn.setOnClickListener{
            var uname = username.text.toString()
            var pwd = password.text.toString()
            if(!uname.isNullOrBlank() && !pwd.isNullOrBlank()){
                loginUser(uname,pwd)
            }
            else{
                Toast.makeText(this@login_page,"Please fill all fields",Toast.LENGTH_SHORT).show()
            }
        }

        // SIGN-UP BUTTON TO REDIRECT TO SIGN UP PAGE
        signupBtn.setOnClickListener{
            val intent = Intent(this, signup_page::class.java)
            startActivity(intent)
        }


    }
    private fun loginUser(username:String,password:String){
        dbRef.orderByChild("userUname").equalTo(username).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(userSnapshot in dataSnapshot.children){
                       val userData = userSnapshot.getValue((user_model::class.java))
                        if(userData!=null && userData.userPwd==password){
                            Toast.makeText(this@login_page,"Welcome, "+userData.userUname+"!",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@login_page, orderways_page::class.java)
                            intent.putExtra("userUname",userData.userUname)
                            startActivity(intent)
                            finish()
                            return
                        }
                        else if(userData!=null && userData.userPwd!=password){
                            Toast.makeText(this@login_page,"Incorrect Credentials",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                Toast.makeText(this@login_page,"Incorrect Credentials",Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@login_page,"Database Error: ${error.message}",Toast.LENGTH_SHORT).show()
            }
        })
    }



}