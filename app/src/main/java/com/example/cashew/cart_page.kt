package com.example.cashew

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
import com.example.cashew.orderSummary_dialog
import pl.droidsonroids.gif.GifImageView

class cart_page : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private var cartList : ArrayList<cart_model> = ArrayList()
    private var drawableResource : Int? = 0
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    public var totalCart:Float = 0f
    lateinit var totalAmount :TextView
    lateinit var cartEmpty:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val cashewGif: GifImageView = findViewById(R.id.userCashewProducts)


        //TOP MENU
        val productButton: ImageButton = findViewById(R.id.productButton2)
        val cartButton : ImageButton = findViewById(R.id.cartButton2)
        val profileBtn : ImageButton = findViewById(R.id.profileBtn2)
        val orderWay: TextView = findViewById(R.id.orderWay)
        val changeOption:TextView = findViewById(R.id.changeOption2)
        val orderSh = getSharedPreferences("orderDetails", MODE_PRIVATE)

        recyclerView = findViewById(R.id.orderRecyclerView)
        totalAmount = findViewById(R.id.totalAmount)
        cartEmpty = findViewById(R.id.cartEmpty)

        // DECLARATION FOR CHECK OUT BUTTON
        val checkOutBtn : Button = findViewById(R.id.checkoutButton)

        // Check Out Dialog
        checkOutBtn.setOnClickListener() {
            if(totalCart>0){
                showDialog()
            }
            else{
                Toast.makeText(this,"Please add items to your cart",Toast.LENGTH_SHORT).show()
            }
        }


        orderWay.setText(orderSh.getString("OrderWay", "").toString())

        val userName: TextView = findViewById(R.id.uNameDisplay)
        val cashewCoins: TextView = findViewById(R.id.cashewCoinsDisplay)
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)
        val userCashew = sh.getString("Wardrobe", "").toString()


        getCart(sh.getString("ID","").toString(),this)
        recyclerView.setLayoutManager(LinearLayoutManager(this,RecyclerView.VERTICAL, false))

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
    public fun getCart(userID:String,context: Context) {
        dbRef = FirebaseDatabase.getInstance().getReference("Cart")
        dbRef.child("cartItems_"+userID).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    totalCart = 0f
                    cartList.clear()
                    for(cartSnapshot in snapshot.children){
                        var cartModel = cartSnapshot.getValue(cart_model::class.java)
                         cartList.add(cartModel!!)
                        totalCart += cartModel.totalPriceOf!!
                    }
                    val cartRecycler = cart_recycler(cartList,userID,context)
                    recyclerView.adapter = cartRecycler
                    totalAmount.text = "Total:\n₱"+totalCart.toString()
                }
                else{
                    cartList.clear()
                    val cartRecycler = cart_recycler(cartList,userID,context)
                    recyclerView.adapter = cartRecycler
                    cartEmpty.setVisibility(View.VISIBLE)
                    totalAmount.text = "Total:\n₱0"
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        Log.d("Cart",""+cartList)
    }

    public fun refreshCart(){
        recyclerView.adapter?.notifyDataSetChanged()
    }

    fun showDialog() {
        val fragmentManager = supportFragmentManager
        val newFragment = orderSummary_dialog()
        newFragment.show(supportFragmentManager, "orderSummary_dialog")

    }
}