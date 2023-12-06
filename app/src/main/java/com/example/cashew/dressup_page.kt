package com.example.cashew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference

class dressup_page : AppCompatActivity() {
    lateinit var databaseRef : DatabaseReference
    val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressup_page)
        val glassesBtn: ImageButton = findViewById(R.id.glassesBtn)
        var uID = sh.getString("ID","").toString()

        glassesBtn.setOnClickListener {
            changePicture()
            var updateWardrobe = mapOf<String, String>("userCashew" to "glasses")
            databaseRef.child( "Users").child(uID).setValue(updateWardrobe).addOnSuccessListener {
                val toastRolled = Toast.makeText(this, "Outfit Changed!", Toast.LENGTH_SHORT)
                toastRolled.show()
            }
        }
    }

    private fun changePicture() {



        // Update the screen with the dice roll
        val diceImage: ImageView = findViewById(R.id.imageView)

        // Determine which drawable resource ID to use based on the dice roll
        val drawableResource = when (1) {
            1 -> R.drawable.sunniescashew
            else -> R.drawable.smileycashew1
        }
        // Update the ImageView with the correct drawable resource ID
        diceImage.setImageResource(drawableResource)






    }
}