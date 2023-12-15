package com.example.cashew

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.util.Patterns

class signup_page  : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editDoB: TextView
    private lateinit var logInBtn: Button
    private lateinit var nxtBtn: Button
    private lateinit var datePickerBtn : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editDoB = findViewById(R.id.editDoB)
        nxtBtn = findViewById(R.id.nxtBtn)
        logInBtn = findViewById(R.id.backLogInBtn)
        datePickerBtn = findViewById(R.id.datePickerBtn)

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener{view,year,month,dayOfMonth ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateLabel(myCalendar)
            //val selectedDate = myCalendar
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        datePickerBtn.setOnClickListener {
            DatePickerDialog(this,datePicker,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        nxtBtn.setOnClickListener {

            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val selectedDate = myCalendar





            if (isValidInput(name, email) && (isValidDob(selectedDate)) ) {


                val intent = Intent(this, signup_page2::class.java)
                intent.putExtra("editName", editName.text.toString() + "")
                intent.putExtra("editEmail", editEmail.text.toString() + "")
                intent.putExtra("editDoB", editDoB.text.toString() + "")

                startActivity(intent)

            }





            logInBtn.setOnClickListener {
                val intent = Intent(this, login_page::class.java)
                startActivity(intent)
            }


        }

    }


    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "MM-dd-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editDoB.setText(sdf.format(myCalendar.time))
    }


    private fun isValidInput(fullname : String, email : String): Boolean {


        if (fullname.isEmpty()) {
            showError("Name cannot be empty")
            return false
        }

        if (email.isEmpty() || !validEmail(email)) {
            showError("Enter a valid email address")
            return false
        }

        return true
    }

    private fun isValidDob(selectedDate: Calendar): Boolean {
        val currentDate = Calendar.getInstance()

        // Check if the selected date is not in the future
        if (selectedDate.after(currentDate)) {
            showError("Please select a valid date of birth.")
            return false
        }

        // Check if the user is at least 18 years old (adjust as needed)
        val minAge = 18
        currentDate.add(Calendar.YEAR, -minAge)
        if (selectedDate.after(currentDate)) {
            showError("You must be at least $minAge years old.")
            return false
        }

        return true
    }



    private fun validEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showError(message: String) {
            showToast(message)
        }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }




    }