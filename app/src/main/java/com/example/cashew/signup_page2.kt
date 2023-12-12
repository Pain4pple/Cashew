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

class signup_page2  : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
//    private lateinit var editName: EditText
    private lateinit var editUname: EditText
//    private lateinit var editEmail: EditText
    private lateinit var editPwd: EditText
//    private lateinit var editDoB: EditText
    private lateinit var rgtBtn: Button
    private lateinit var logInBtn: Button
    private lateinit var dbRefNested: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page2)

        editUname = findViewById(R.id.editUname)
        editPwd = findViewById(R.id.editPassword)
        rgtBtn = findViewById(R.id.registerBtn)
        logInBtn = findViewById(R.id.backLogInBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        //dbRefNested = FirebaseDatabase.getInstance().getReference("Purchases")

        rgtBtn.setOnClickListener{
            saveUserData()
        }

        logInBtn.setOnClickListener{
            val intent = Intent(this, login_page::class.java)
            startActivity(intent)
        }




    }

    private fun saveUserData(){
//        var userName = editName.text.toString()
//        var userEmail= editEmail.text.toString()
        var userUname= editUname.text.toString()
        var userPwd= editPwd.text.toString()
//        var userDob= editDoB.text.toString()
        var userDob= intent.extras?.getString("editDoB") ?: "No DoB"
        var userEmail= intent.extras?.getString("editEmail") ?: "No Email"
        var userName= intent.extras?.getString("editName") ?: "No Username"

        val userID = "USER_"+dbRef.push().key!!
        val user = user_model(userID,userName,userEmail,userUname,userPwd,userDob)

        dbRef.child(userID).setValue(user).addOnSuccessListener {
            Toast.makeText(this, "Successfully registered",Toast.LENGTH_LONG).show()
            val intent = Intent(this, login_page::class.java)
            startActivity(intent)
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to register",Toast.LENGTH_LONG).show()
        }
        dbRefNested = FirebaseDatabase.getInstance().getReference("Purchases")
        val path = dbRefNested.child(userID)



    }


}