package com.example.cashew

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class changepassword_page : AppCompatActivity() {


    private lateinit var oldPasswordEditTxt: EditText
    private lateinit var newPasswordEditTxt: EditText
    private lateinit var confirmPasswordEditTxt: EditText
    private lateinit var changePasswordBtn: Button

    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var sh: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepassword_page)

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.reference.child("Users")
        sh = getSharedPreferences("currentUserDetails", AppCompatActivity.MODE_PRIVATE)

        oldPasswordEditTxt = findViewById(R.id.oldPasswdEditText)
        newPasswordEditTxt = findViewById(R.id.newPasswdEditText)
        confirmPasswordEditTxt = findViewById(R.id.confirmPasswdEditText)
        changePasswordBtn = findViewById(R.id.changePwdBtn)


        changePasswordBtn.setOnClickListener {
            val oldPassword = oldPasswordEditTxt.text.toString()
            val newPassword = newPasswordEditTxt.text.toString()
            val confirmPassword = confirmPasswordEditTxt.text.toString()

            if (changePassword(oldPassword, newPassword, confirmPassword)) {

                updatePassword()

            }

        }


    }

    private fun changePassword(oldPassword : String, newPassword : String, confirmPassword : String): Boolean {


        if (oldPassword.isNullOrBlank() || newPassword.isNullOrBlank() || confirmPassword.isNullOrBlank()) {
            showError("Please complete password input fields.")

            if (newPassword.length < 6) {
                showError("Name must be at least 6 characters long.")
                return false

            }

            return false
        }

        // CHECK DATABASE IF OLD PASSWORD MATCH CURRENT PASSWORD
        val currentPassword = dbRef.child("userPwd").toString()
        val userID = sh.getString("ID","").toString()

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        dbRef.child("USER_"+userID)



        Log.i("PasswordValidation", "Entered Password: $currentPassword")
        Log.i("PasswordValidation", "User ID: $userID")



        if (oldPassword == newPassword) {
            showError("Old Password must not be similar to New Password.")

            Log.i("PasswordValidation", "Entered Password: $oldPassword")
            Log.i("PasswordValidation", "Entered Confirm Password: $newPassword")
            return false
        }

        if (newPassword != confirmPassword) {
            showError("Passwords do not match.")

            Log.i("PasswordValidation", "Entered Password: $newPassword")
            Log.i("PasswordValidation", "Entered Confirm Password: $confirmPassword")
            return false
        }

        return true
        Log.i("PasswordValidation", "Passwords entered: o$oldPassword, $newPassword , $confirmPassword")
    }

    private fun updatePassword() {

    }



    private fun showError(message: String) {
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}