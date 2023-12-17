package com.example.cashew

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.cashew.models.cart_model
import com.example.cashew.models.order_model
import com.example.cashew.models.user_model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class changepassword_page : AppCompatActivity() {


    private lateinit var oldPasswordEditTxt: EditText
    private lateinit var newPasswordEditTxt: EditText
    private lateinit var confirmPasswordEditTxt: EditText
    private lateinit var changePasswordBtn: Button

    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var sh: SharedPreferences
    lateinit var userPwd : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepassword_page)

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.reference.child("Users")
        sh = getSharedPreferences("currentUserDetails", AppCompatActivity.MODE_PRIVATE)
        userPwd = ""
        oldPasswordEditTxt = findViewById(R.id.oldPasswdEditText)
        newPasswordEditTxt = findViewById(R.id.newPasswdEditText)
        confirmPasswordEditTxt = findViewById(R.id.confirmPasswdEditText)
        changePasswordBtn = findViewById(R.id.changePwdBtn)
        val exitBtn: ImageButton = findViewById(R.id.exitBtn)
        val userID = sh.getString("ID", "").toString()

        getUserPassword()
        changePasswordBtn.setOnClickListener {
            val oldPassword = oldPasswordEditTxt.text.toString()
            val newPassword = newPasswordEditTxt.text.toString()
            val confirmPassword = confirmPasswordEditTxt.text.toString()



            if(changePassword(oldPassword, newPassword, confirmPassword)) {
                updatePassword(newPassword)

            }




        }

        exitBtn.setOnClickListener {
            val intent = Intent(this, profile_page::class.java)
            startActivity(intent)
        }


    }


    //private fun getUserPassword(userID: String) {
    private fun getUserPassword() {
        val userID = sh.getString("ID", "").toString()
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        val userRef = dbRef.child("$userID")


        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    userPwd = ""
                    val currentUser = dataSnapshot.getValue(user_model::class.java)
                    if (currentUser != null) {
                         userPwd = currentUser.userPwd ?: ""
                        Log.i("Password Retrieval", "Retrieved Password: $userPwd")
                        // Now you can use the userPwd as needed
                    }
                } else {
                    Log.e("Password Retrieval", "User data not found for USER_$userID")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Password Retrieval", "Error retrieving user data: ${databaseError.message}")
            }
        })


    }

    private fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String): Boolean {
        if (oldPassword.isNullOrBlank() || newPassword.isNullOrBlank() || confirmPassword.isNullOrBlank()) {
            showError("Please complete password input fields.")

            if (newPassword.length < 6) {
                showError("Name must be at least 6 characters long.")

                return false
            }

            return false
        }

        //getUserPassword()

        if (!oldPassword.equals(userPwd)) {
            showError("Old Password does not match Current Password.")

            Log.i("PasswordValidation", "Entered Password: $oldPassword")
            Log.i("PasswordValidation", "Entered Current Password: $userPwd")
            return false
        }



        if (!newPassword.equals(confirmPassword)) {
            showError("Please Re-type password.")

            Log.i("PasswordValidation", "Entered Password: $newPassword")
            Log.i("PasswordValidation", "Entered Confirm Password: $confirmPassword")
            return false
        }

        Log.i("PasswordValidation", "Entered old: $oldPassword")
        Log.i("PasswordValidation", "Entered current: $userPwd")
        Log.i("PasswordValidation", "Entered new: $newPassword")
        Log.i("PasswordValidation", "Entered confirm: $confirmPassword")




        return true
    }

    private fun updatePassword(newPassword: String) {
        val userID = sh.getString("ID", "").toString()
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        val userRef = dbRef.child("$userID")
        Log.i("PasswordValidation", "current user: $userID")

        userRef.child("userPwd").setValue(newPassword)
            .addOnSuccessListener {
                showToast("Password updated successfully!")
                val intent = Intent(this, login_page::class.java)
                //sh.edit().clear().apply()
                startActivity(intent)

                Log.i("PasswordValidation", "updated password: $newPassword")
                // Handle any additional logic after successful password update
            }
            .addOnFailureListener { e ->
                showError("Error updating password: ${e.message}")
            }
    }

    private fun showError(message: String) {
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



}







    /*
private fun getUserPassword() {
    val userID = sh.getString("ID", "").toString()
    dbRef = FirebaseDatabase.getInstance().getReference("Users")

    dbRef.child("USER_" + userID).addValueEventListener(object :
        ValueEventListener { //Sets path to firebase
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            //if (dataSnapshot.exists()) {
            userList.clear()
            currentPwd = ""

            Log.i("Check User", "user ${dataSnapshot.toString()}")

            var currentUser = dataSnapshot.getValue(user_model::class.java)
            Log.i("San Miguel", "Current : $currentUser")

            for (userSnapshot in dataSnapshot.children) {

                //var userSnapshot = dataSnapshot.child()
               // var currentUser = userSnapshot.getValue(user_model::class.java)
                if(userSnapshot.child("userPwd").value == currentUser!!.userPwd) {
                    Log.i("San Miguel", "Current : $currentUser")
                }

                    //Log.i("Check User", "user $currentUser")

                if (currentUser != null) {
                    //userList.add(currentUser!!)
                    currentPwd = currentUser.userPwd ?: ""
                    Log.i("Password Get", "current " + currentUser.userPwd)
                }
            Log.i("Password Get", "current " + currentUser.userPwd)

            }



            //}
        }


        override fun onCancelled(databaseError: DatabaseError) {
            // Handle errors
            Log.e("OrderSummaryDialog", "Error retrieving data: ${databaseError.message}")
        }


    })
}




    private fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String): Boolean {



        if (oldPassword.isNullOrBlank() || newPassword.isNullOrBlank() || confirmPassword.isNullOrBlank()) {
            showError("Please complete password input fields.")

            if (newPassword.length < 6) {
                showError("Name must be at least 6 characters long.")

                return false
            }

            return false
        }

        if (!oldPassword.equals(currentPwd)) {
            showError("Passwords do not match.")

            Log.i("PasswordValidation", "Entered Password: $oldPassword")
            Log.i("PasswordValidation", "Entered Current Password: $currentPwd")
            return false
        }

        if (!newPassword.equals(confirmPassword)) {
            showError("passwords do not match.")

            Log.i("PasswordValidation", "Entered Password: $newPassword")
            Log.i("PasswordValidation", "Entered Confirm Password: $confirmPassword")
            return false
        }

        Log.i("PasswordValidation", "Entered old: $oldPassword")
        Log.i("PasswordValidation", "Entered current: $currentPwd")
        Log.i("PasswordValidation", "Entered new: $newPassword")
        Log.i("PasswordValidation", "Entered confirm: $confirmPassword")




        return true
    }












    private fun updatePassword(newPassword: String) {
        val userID = sh.getString("ID", "").toString()
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        val userRef = dbRef.child("USER_$userID")

        userRef.child("userPwd").setValue(newPassword)
            .addOnSuccessListener {
                showToast("Password updated successfully!")
                // Handle any additional logic after successful password update
            }
            .addOnFailureListener { e ->
                showError("Error updating password: ${e.message}")
            }
    }





    private fun showError(message: String) {
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

 */