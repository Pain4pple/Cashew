package com.example.cashew

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashew.models.cart_model
import com.example.cashew.models.product_model
import com.example.cashew.models.user_model
import com.example.cashew.objects.cart_recycler
import com.example.cashew.objects.product_recycler
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pl.droidsonroids.gif.GifImageView

class cart_page : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private var cartList : ArrayList<cart_model> = ArrayList()
    private var drawableResource : Int? = 0
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val cashewGif: GifImageView = findViewById(R.id.userCashewProducts)

        val productButton: ImageButton = findViewById(R.id.productButton2)
        val cartButton : ImageButton = findViewById(R.id.cartButton2)
        val profileBtn : ImageButton = findViewById(R.id.profileBtn2)
        val orderWay: TextView = findViewById(R.id.orderWay)
        val changeOption:TextView = findViewById(R.id.changeOption2)
        val orderSh = getSharedPreferences("orderDetails", MODE_PRIVATE)
        recyclerView = findViewById(R.id.orderRecyclerView)


        orderWay.setText(orderSh.getString("OrderWay", "").toString())

        val userName: TextView = findViewById(R.id.uNameDisplay)
        val cashewCoins: TextView = findViewById(R.id.cashewCoinsDisplay)
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
        val userCashew = sh.getString("Wardrobe", "").toString()


        getCart(sh.getString("ID","").toString())

        recyclerView.setLayoutManager(LinearLayoutManager(this,RecyclerView.VERTICAL, false))
        val cartRecycler = cart_recycler(cartList,sh.getString("ID","").toString(),this)
        recyclerView.adapter = cartRecycler

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

        changeOption.setOnClickListener{
            val intent = Intent(this, orderways_page::class.java)
            startActivity(intent)
        }
        productButton.setOnClickListener {
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
        }

        cartButton.setOnClickListener {
            Toast.makeText(this,"You are already here!",Toast.LENGTH_SHORT).show()
        }


        profileBtn.setOnClickListener {
            val intent = Intent(this, profile_page::class.java)
            startActivity(intent)
        }
        cashewGif.setOnClickListener{
            val intent = Intent(this, dressup_page::class.java)
            startActivity(intent)
        }

    }
    public fun getCart(userID:String): ArrayList<cart_model> {
        cartList.clear()
        dbRef = FirebaseDatabase.getInstance().getReference("Cart")
        dbRef.child("cartItems_"+userID).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(cartSnapshot in snapshot.children){
                        var cartModel = cartSnapshot.getValue(cart_model::class.java)
                         cartList.add(cartModel!!)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        return cartList
    }
}