package com.example.cashew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.cashew.models.outfit_model
import com.example.cashew.models.user_model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pl.droidsonroids.gif.GifImageView

class dressup_page : AppCompatActivity() {

    //DECLARE DATABASE TO SAVE ICON UPON CHANGE
    private lateinit var dbRef : DatabaseReference
    private lateinit var dbRef2 : DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var txtBalance : TextView
    private var cashewCurrentPrice : Int = 0
    private var cashewCurrentOutfit : String = ""
    private var cashewCurrentID : String = ""
    private var counter:Int=0
    private var outfitList : ArrayList<outfit_model> = ArrayList()
    private var drawableResource : Int? = 0
    private var drawedImage : String = ""
    private var userCashew:String = ""
    private var currentCashew:String = ""
    private var userID:String = ""
    private var userCashewBalance:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressup_page)

        outfitList.add(outfit_model("1","sunnies",R.drawable.sunnies,100,R.drawable.sunniescashew))
        outfitList.add(outfit_model("2","octo",R.drawable.octoglasses,200,R.drawable.octocashew))
        outfitList.add(outfit_model("3","bow",R.drawable.bowtie,150,R.drawable.bowcashew))
        outfitList.add(outfit_model("4","mcdo",R.drawable.mcdohat,300,R.drawable.macdo_slave))

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.reference.child("Users")
        dbRef2 = FirebaseDatabase.getInstance().getReference("Users").child(userID)

        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
        currentCashew = sh.getString("Wardrobe", "").toString()
        cashewCurrentOutfit = currentCashew
        Log.d("Compare",currentCashew)
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
        val updateIconBtn : Button = findViewById(R.id.cancelOrderlBtn)
        val price : TextView = findViewById(R.id.outfitPrice)

        val outfitBtn : ImageButton = findViewById(R.id.outfitBtn)
        val nextBtn : ImageButton = findViewById(R.id.nextBtn)
        val prevBtn : ImageButton = findViewById(R.id.prevBtn)

        txtBalance = findViewById(R.id.coinBalance)

        var currentOutfit = outfitList.get(counter)
        outfitBtn.setImageResource(currentOutfit.outfitDrawable)
        price.text = ""+currentOutfit.outfitPrice
        getCoins(userID)

        exitBtn.setOnClickListener {
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
        }

        updateIconBtn.setOnClickListener {
            updateAvatar()
            txtBalance.text = sh.getInt("Coins",0).toString()
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
            cashewCurrentPrice = currentOutfit.outfitPrice!!
            cashewCurrentOutfit = currentOutfit.outfitName!!
            cashewCurrentID = currentOutfit.outfitID!!
            Log.d("Compare",cashewCurrentOutfit)

        }
    }

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
        currentCashew = sh.getString("Wardrobe","").toString()
        Log.d("Compare",cashewCurrentOutfit +" = "+currentCashew)


        if(cashewCurrentOutfit.equals(currentCashew)){
            Toast.makeText(this, "Err, you haven't changed anything!", Toast.LENGTH_SHORT).show()
        }
        else {

            if(userCashewBalance<cashewCurrentPrice!!){
                Toast.makeText(this,"You don't have enough money!",Toast.LENGTH_SHORT).show()
            }
            else{
                var newBal = userCashewBalance - cashewCurrentPrice!!
                myEdit.putInt("Coins", newBal)
                myEdit.apply()
                val userCashew = drawedImage
                Log.i("Cashew", "Outfit: " + userCashew)

                dbRef.child(userID).child("userCashew").setValue(userCashew).addOnSuccessListener {
                    Toast.makeText(this, "Woof!", Toast.LENGTH_SHORT).show()
                    myEdit.putString("Wardrobe", userCashew)
                    myEdit.apply()
                    currentCashew = sh.getString("Wardrobe","").toString()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to change", Toast.LENGTH_SHORT).show()
                }

                dbRef.child(userID).child("userCashewCoins").setValue(newBal).addOnSuccessListener {
                getCoins(userID)
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to buy cosmetic", Toast.LENGTH_SHORT).show()
                }

                dbRef.child(userID).child("purchasedOutfits").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        dbRef2.child(userID).child("purchasedOutfits").child(cashewCurrentID).setValue(userCashew).addOnSuccessListener {

                        }.addOnCanceledListener {
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
            }
        }
    }

    private fun getCoins(userID: String) {
        var coinsRef = firebaseDatabase.getReference("Users")
        coinsRef.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val userDetails = snapshot.getValue(user_model::class.java)
                    txtBalance.text = userDetails!!.userCashewCoins.toString()
                    userCashewBalance = userDetails!!.userCashewCoins!!
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("action","Error: "+error)
            }
        })
    }

}