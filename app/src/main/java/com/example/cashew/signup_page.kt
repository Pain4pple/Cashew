package com.example.cashew

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cashew.models.user_model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup_page  : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var editName: EditText
    private lateinit var editUname: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPwd: EditText
    private lateinit var editDoB: EditText
    private lateinit var rgtBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)
<<<<<<< Updated upstream

        var
=======
        editName = findViewById(R.id.editName)
        editUname = findViewById(R.id.editUname)
        editEmail = findViewById(R.id.editEmail)
        editPwd = findViewById(R.id.editPassword)
        editDoB = findViewById(R.id.editDoB)
        rgtBtn = findViewById(R.id.registerBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        rgtBtn.setOnClickListener{
            saveUserData()
        }
    }

    private fun saveUserData(){
        var userName = editName.text.toString()
        var userEmail= editEmail.text.toString()
        var userUname= editUname.text.toString()
        var userPwd= editPwd.text.toString()
        var userDob= editDoB.text.toString()

        val userID = dbRef.push().key!!
        val user = user_model(userID,userName,userEmail,userUname,userPwd,userDob)

        dbRef.child("USER_"+userID).setValue(user).addOnSuccessListener {
            Toast.makeText(this, "Successfully registered",Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to register",Toast.LENGTH_LONG).show()
        }
>>>>>>> Stashed changes
    }
}