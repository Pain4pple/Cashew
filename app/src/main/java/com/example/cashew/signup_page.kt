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
import com.example.cashew.models.user_model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        datePickerBtn.setOnClickListener {
            DatePickerDialog(this,datePicker,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

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

    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "MM-dd-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editDoB.setText(sdf.format(myCalendar.time))
    }


}