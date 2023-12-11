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
import android.widget.TextView
import android.widget.Toast
import com.example.cashew.models.outfit_model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import pl.droidsonroids.gif.GifImageView

class dressup_page : AppCompatActivity() {

    //DECLARE DATABASE TO SAVE ICON UPON CHANGE
    private lateinit var dbRef : DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var updateIconBtn : Button
    private var counter:Int=0
    private var outfitList : ArrayList<outfit_model> = ArrayList()
    private var drawableResource : Int? = 0
    private var drawedImage : String = ""
    private var userCashew:String = "default"
    private var currentCashew:String = ""
    private var userID:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressup_page)

        outfitList.add(outfit_model("1","sunnies",R.drawable.sunnies,100))
        outfitList.add(outfit_model("2","octo",R.drawable.octoglasses,200))
        outfitList.add(outfit_model("3","bow",R.drawable.bowtie,150))
        outfitList.add(outfit_model("4","mcdo",R.drawable.mcdohat,300))

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.reference.child("Users")
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
        currentCashew = sh.getString("Wardrobe", "").toString()
        userID = sh.getString("ID", "").toString()
        val mainCashew: GifImageView = findViewById(R.id.userCashewWardrobe)

        drawableResource = when (currentCashew) {
            "sunnies" -> R.drawable.sunniescashew
            "octo" -> R.drawable.octocashew
            "bow" -> R.drawable.bowcashew
            "mcdo" -> R.drawable.macdo_slave
            "default" -> R.drawable.cashew1
            else -> R.drawable.cashew1
        }
        mainCashew.setImageResource(drawableResource!!)


        // WARDROBE VARIABLES
        val exitBtn : ImageButton = findViewById(R.id.exitBtn)
        val updateIconBtn : Button = findViewById(R.id.updateBtn)
        val price : TextView = findViewById(R.id.priceOutfit)

        val outfitBtn : ImageButton = findViewById(R.id.outfitBtn)
        val nextBtn : ImageButton = findViewById(R.id.nextBtn)
        val prevBtn : ImageButton = findViewById(R.id.prevBtn)

        var currentOutfit = outfitList.get(counter)
        outfitBtn.setImageResource(currentOutfit.outfitDrawable)
        price.text = ""+currentOutfit.outfitPrice

        exitBtn.setOnClickListener {
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
        }

        updateIconBtn.setOnClickListener {
                updateAvatar()
        }

        nextBtn.setOnClickListener{
            if(outfitList.size-1!=counter){
                counter+=1
            }
            else{
                counter=0
            }
            var currentOutfit = outfitList.get(counter)
            outfitBtn.setImageResource(currentOutfit.outfitDrawable)
            price.text = ""+currentOutfit.outfitPrice
        }

        prevBtn.setOnClickListener {
            if(counter!=0){
                counter-=1
            }
            else{
                counter=outfitList.size-1
            }
            var currentOutfit = outfitList.get(counter)
            outfitBtn.setImageResource(currentOutfit.outfitDrawable)
            price.text = ""+currentOutfit.outfitPrice
        }

        outfitBtn.setOnClickListener{
            var currentOutfit = outfitList.get(counter)
            userCashew = currentOutfit.outfitName!!
            changePicture(userCashew)
        }
        /*sunniesBtn.setOnClickListener {
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
        }*/



    }

/*    private fun changePicture(image_id: Int) {

        // Update the cashew

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

    }*/
    private fun changePicture(userCashew: String) {

        // Update the cashew
        val mainCashew: GifImageView = findViewById(R.id.userCashewWardrobe)

        // Determine which drawable resource ID to use based on the image_id selected
        drawableResource = when (userCashew) {
            "sunnies" -> R.drawable.sunniescashew
            "octo" -> R.drawable.octocashew
            "bow" -> R.drawable.bowcashew
            "mcdo" -> R.drawable.macdo_slave
            "default" -> R.drawable.cashew1
            else -> R.drawable.cashew1
        }

        // Update the ImageView with the correct drawable resource ID
        mainCashew.setImageResource(drawableResource!!)

        //Pass value of drawableResource to drawedImage
        drawedImage = userCashew

    }
    private fun updateAvatar() {
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
        val myEdit = sh.edit()

        if(userCashew.equals(currentCashew)){
            Toast.makeText(this, "Err, you haven't changed anything!", Toast.LENGTH_SHORT)
        }
        else {

            val userCashew = drawedImage
            Log.i("Cashew", "Outfit: " + userCashew)

            dbRef.child(userID).child("userCashew").setValue(userCashew).addOnSuccessListener {
                Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_SHORT).show()
                myEdit.putString("Wardrobe", userCashew)
                currentCashew = sh.getString("Wardrobe","").toString()
                myEdit.apply()

            }.addOnFailureListener {
                Toast.makeText(this, "Failed to change", Toast.LENGTH_SHORT).show()
            }

        }
    }

}