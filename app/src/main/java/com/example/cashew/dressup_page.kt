package com.example.cashew

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast

class dressup_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressup_page)

        // WARDROBE VARIABLES
        val exitBtn : ImageButton = findViewById(R.id.exitBtn)
        val sunniesBtn : ImageButton = findViewById(R.id.sunniesBtn)
        val octoBtn : ImageButton = findViewById(R.id.octoBtn)
        val bowBtn : ImageButton = findViewById(R.id.bowBtn)
        val mchatBtn : ImageButton = findViewById(R.id.mchatBtn)

        exitBtn.setOnClickListener {
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
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
        val drawableResource = when (image_id) {
            1 -> R.drawable.sunniescashew
            2 -> R.drawable.octocashew
            3 -> R.drawable.bowcashew
            4 -> R.drawable.mcdocashew
            else -> R.drawable.smileycashew1
        }

        // Update the ImageView with the correct drawable resource ID
        mainCashew.setImageResource(drawableResource)

    }

}