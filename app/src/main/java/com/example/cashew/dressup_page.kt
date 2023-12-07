package com.example.cashew

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class dressup_page : AppCompatActivity() {

    //DECLARE DATABASE TO SAVE ICON UPON CHANGE
    private lateinit var dbRef : DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var updateIconBtn : Button
    private var drawableResource : Int? = 0
    private var drawedImage : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressup_page)

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.reference.child("Users")


        // WARDROBE VARIABLES
        val exitBtn : ImageButton = findViewById(R.id.exitBtn)
        val sunniesBtn : ImageButton = findViewById(R.id.sunniesBtn)
        val octoBtn : ImageButton = findViewById(R.id.octoBtn)
        val bowBtn : ImageButton = findViewById(R.id.bowBtn)
        val mchatBtn : ImageButton = findViewById(R.id.mchatBtn)
        val updateIconBtn : Button = findViewById(R.id.updateBtn)

        exitBtn.setOnClickListener {
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
        }

        updateIconBtn.setOnClickListener {
            updateAvatar()
        }



        sunniesBtn.setOnClickListener {
            val image_id : Int = 1
            changePicture(image_id)
            val toastRolled = Toast.makeText(this, "Outfit Changed!", Toast.LENGTH_SHORT)
            toastRolled.show()
        }

        octoBtn.setOnClickListener {
            val image_id : Int = 2
            changePicture(image_id)
            val toastRolled = Toast.makeText(this, "Outfit Changed!", Toast.LENGTH_SHORT)
            toastRolled.show()
        }

        bowBtn.setOnClickListener {
            val image_id : Int = 3
            changePicture(image_id)
            val toastRolled = Toast.makeText(this, "Outfit Changed!", Toast.LENGTH_SHORT)
            toastRolled.show()
        }

        mchatBtn.setOnClickListener {
            val image_id : Int = 4
            changePicture(image_id)
            val toastRolled = Toast.makeText(this, "Outfit Changed!", Toast.LENGTH_SHORT)
            toastRolled.show()
        }




    }

    private fun changePicture(image_id: Int) {

        // Update the cashew
        val mainCashew: ImageView = findViewById(R.id.mainCashew)

        // Determine which drawable resource ID to use based on the image_id selected
        drawableResource = when (image_id) {
            1 -> R.drawable.sunniescashew
            2 -> R.drawable.octocashew
            3 -> R.drawable.bowcashew
            4 -> R.drawable.mcdocashew
            else -> R.drawable.smileycashew1
        }

        // Update the ImageView with the correct drawable resource ID
        mainCashew.setImageResource(drawableResource!!)

        //Pass value of drawableResource to drawedImage
        drawedImage = image_id

    }

    private fun updateAvatar() {

        val userCashew = drawedImage
        Log.i("amado", userCashew.toString())

        dbRef.child("userCashew").setValue(userCashew).addOnSuccessListener {
            Toast.makeText(this, "Successfully uploaded",Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to register",Toast.LENGTH_LONG).show()
        }







    }

}