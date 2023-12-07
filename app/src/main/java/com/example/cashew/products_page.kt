package com.example.cashew

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashew.models.product_model
import com.example.cashew.objects.product_recycler
import pl.droidsonroids.gif.GifImageView


class products_page : AppCompatActivity() {
    //var currentUser = User.currentUser
    private lateinit var recyclerView : RecyclerView
    private var productList : ArrayList<product_model> = ArrayList()
    private var drawableResource : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        productList.add(product_model("1","Chocolate Cake",R.drawable.chocolate_cake,500))
        productList.add(product_model("2","Cookies",R.drawable.cookies,600))
        productList.add(product_model("3","Cinnamon Roll",R.drawable.cinnamon_roll,200))
        productList.add(product_model("4","Strawberry",R.drawable.strawberrysandwich,600))

        recyclerView = findViewById(R.id.productsView)
        recyclerView.setLayoutManager(GridLayoutManager(this, 2))
        val productRecycler = product_recycler(productList)
        recyclerView.adapter = productRecycler

        //DECLARATIONS
        val cashewGif:GifImageView = findViewById(R.id.userCashewProducts)
        val cashewBtn: ImageButton = findViewById(R.id.favoritebutton)
        val productsBtn : ImageButton = findViewById(R.id.productsBtn)
        val profileBtn : ImageButton = findViewById(R.id.profileBtn)
        val userName: TextView = findViewById(R.id.uNameDisplay)
        val cashewCoins: TextView = findViewById(R.id.cashewCoinsDisplay)
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
        val userCashew = sh.getString("Wardrobe", "").toString()

        drawableResource = when (userCashew) {
            "sunnies" -> R.drawable.sunniescashew
            "octo" -> R.drawable.octocashew
            "bow" -> R.drawable.bowcashew
            "mcdo" -> R.drawable.macdo_slave
            "default" -> R.drawable.cashew1
            else -> R.drawable.cashew1
        }
        cashewGif.setImageResource(drawableResource!!)

        if (sh.getString("ID","") != null) {
            userName.text = "Hi! "+sh.getString("Username", "")
            cashewCoins.text = ""+sh.getInt("Coins", 0)
        }
        else{
            userName.text = "who you?"
            cashewCoins.text = "you have no money"
        }

        cashewBtn.setOnClickListener {
            val intent = Intent(this, dressup_page::class.java)
            startActivity(intent)
        }

        productsBtn.setOnClickListener {
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
        }


        profileBtn.setOnClickListener {
            val intent = Intent(this, profile_page::class.java)
            startActivity(intent)
        }

    }




}