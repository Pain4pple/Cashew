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
import android.util.Log


class signup_page2  : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
//    private lateinit var editName: EditText
    private lateinit var editUname: EditText
//    private lateinit var editEmail: EditText
    private lateinit var editPwd: EditText
//    private lateinit var editDoB: EditText
    
//  private lateinit var editconfirmpasswd : EditText
    private lateinit var rgtBtn: Button
    private lateinit var logInBtn: Button
    private lateinit var dbRefNested: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page2)

        editUname = findViewById(R.id.editUname)
        editPwd = findViewById(R.id.editPassword)
        val editconfirmpasswd : EditText = findViewById(R.id.editConfirm)
        rgtBtn = findViewById(R.id.registerBtn)
        logInBtn = findViewById(R.id.backLogInBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        //dbRefNested = FirebaseDatabase.getInstance().getReference("Purchases")

        rgtBtn.setOnClickListener{

            val username = editUname.text.toString()
            val password = editPwd.text.toString()
            val confirmPwd  = editconfirmpasswd.text.toString()



            if (isValidInput(username, password, confirmPwd) )  {

                saveUserData()




            }
            //saveUserData()


        }

        logInBtn.setOnClickListener{
            val intent = Intent(this, login_page::class.java)
            startActivity(intent)
        }




    }

    private fun isValidInput(username : String, password : String, confirmPwd : String): Boolean {

        if (username.isEmpty()) {
            showError("Name cannot be empty.")


            if (username.length < 3) {
                showError("Name must be at least 3 characters long.")
                return false

            }
            return false
        }


        if (confirmPwd.isEmpty() ) {
            showError("Re-type password to confirm.")
            return false
        }


        if (password.isEmpty() || password.length < 6) {
            showError("Password must be at least 6 characters long.")


            return false
        }



        if (password != confirmPwd) {
            showError("Passwords do not match.")

            Log.i("PasswordValidation", "Entered Password: $password")
            Log.i("PasswordValidation", "Entered Confirm Password: $confirmPwd")
            return false
        }

        Log.i("PasswordValidation", "Entered Password: $password")
        Log.i("PasswordValidation", "Entered Confirm Password: $confirmPwd")









        return true
    }



    private fun showError(message: String) {
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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