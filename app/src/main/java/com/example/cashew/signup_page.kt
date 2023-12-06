package com.example.cashew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cashew.models.user_model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup_page  : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editDoB: EditText
    private lateinit var logInBtn: Button
    private lateinit var nxtBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editDoB = findViewById(R.id.editDoB)
        nxtBtn = findViewById(R.id.nxtBtn)
        logInBtn = findViewById(R.id.backLogInBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        nxtBtn.setOnClickListener{
            val intent = Intent(this, signup_page2::class.java)
            intent.putExtra("editName", editName.text.toString()+"")
            intent.putExtra("editEmail", editEmail.text.toString()+"")
            intent.putExtra("editDoB", editDoB.text.toString()+"")
            startActivity(intent)
        }

        logInBtn.setOnClickListener{
            val intent = Intent(this, login_page::class.java)
            startActivity(intent)
        }




    }


}