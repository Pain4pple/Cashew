package com.example.cashew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cashew.models.user_model
import com.example.cashew.objects.User
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
    private lateinit var confPwd: EditText
    private lateinit var UserCon: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page2)

        editUname = findViewById(R.id.editUname)
        editPwd = findViewById(R.id.editPassword)
        rgtBtn = findViewById(R.id.registerBtn)
        logInBtn = findViewById(R.id.backLogInBtn)
        confPwd = findViewById(R.id.confirmPassword)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        rgtBtn.setOnClickListener{
            saveUserData()
        }

        logInBtn.setOnClickListener{
            val intent = Intent(this, login_page::class.java)
            startActivity(intent)
        }




    }

    private fun saveUserData(){
        var userUname= editUname.text.toString()
        var userPwd= editPwd.text.toString()
        var userConfirmPwd= confPwd.text.toString()

        var userDob= intent.extras?.getString("editDoB") ?: "No DoB"
        var userEmail= intent.extras?.getString("editEmail") ?: "No Email"
        var userName= intent.extras?.getString("editName") ?: "No Username"

        val userID = dbRef.push().key!!
        val user = user_model(userID,userName,userEmail,userUname,userPwd,userDob)

        if(userPwd.equals(userConfirmPwd)) {
            dbRef.child("USER_" + userID).setValue(user).addOnSuccessListener {
                Toast.makeText(this, "Successfully registered", Toast.LENGTH_LONG).show()
                val intent2 = Intent(this@signup_page2, login_page::class.java)
                val sharedPreferences = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
                val myEdit = sharedPreferences.edit()

                myEdit.putString("ID",userID)
                myEdit.putString("Username",userUname)
                myEdit.putString("Email",userEmail)
                myEdit.putString("Name",userName)
                myEdit.putString("Wardrobe","default")
                myEdit.putInt("Coins",0)
                myEdit.apply()

//                UserCon.getUserData(userName)

                startActivity(intent2)

            }.addOnFailureListener {
                Toast.makeText(this, "Failed to register", Toast.LENGTH_LONG).show()
            }

        }
        else{
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show()
        }

    }


}