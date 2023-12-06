package com.example.cashew

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashew.models.product_model
import com.example.cashew.objects.product_recycler


class products_page : AppCompatActivity() {
    //var currentUser = User.currentUser
    private lateinit var recyclerView : RecyclerView
    private var productList : ArrayList<product_model> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        productList.add(product_model("1","Lemon Cake",R.drawable.lemon_cake,500))
        productList.add(product_model("2","Pancake",R.drawable.pancake,600))
        productList.add(product_model("3","Cinnamon Roll",R.drawable.cinnamonroll,200))
        productList.add(product_model("4","Strawberry",R.drawable.strawberrysandwich,600))

        recyclerView = findViewById(R.id.productsView)
        recyclerView.setLayoutManager(GridLayoutManager(this, 2))
        val productRecycler = product_recycler(productList)
        recyclerView.adapter = productRecycler

        //DECLARATIONS
        val cashewBtn: ImageButton = findViewById(R.id.favoritebutton)
        val userName: TextView = findViewById(R.id.uNameDisplay)
        val cashewCoins: TextView = findViewById(R.id.cashewCoinsDisplay)
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)


    if (sh.getString("ID","") != null) {
            userName.text = ""+sh.getString("Username", "")
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
    }




}