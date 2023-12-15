package com.example.cashew

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashew.models.cart_model
import com.example.cashew.models.order_model
import com.example.cashew.objects.cart_recycler
import com.example.cashew.objects.order_recycler
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class orderhistory_page : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    private lateinit var recyclerView: RecyclerView
    private var orderList : ArrayList<order_model> = ArrayList()
    private lateinit var orderEmpty:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderhistory)

        val productButton: ImageButton = findViewById(R.id.productButton4)
        val cartButton : ImageButton = findViewById(R.id.cartButton4)
        val profileBtn : ImageButton = findViewById(R.id.profileBtn4)
        val sh = getSharedPreferences("currentUserDetails", MODE_PRIVATE)

        orderEmpty = findViewById(R.id.orderEmpty)
        recyclerView = findViewById(R.id.recyclerViewOrderHistory)

        getOrders(sh.getString("ID","").toString(),this)
        recyclerView.setLayoutManager(LinearLayoutManager(this, RecyclerView.VERTICAL, false))

        productButton.setOnClickListener {
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
        }

        cartButton.setOnClickListener {
            val intent = Intent(this, cart_page::class.java)
            startActivity(intent)
        }


        profileBtn.setOnClickListener {
            val intent = Intent(this, profile_page::class.java)
            startActivity(intent)
        }

    }

    private fun getOrders(userID: String, context: Context) {
        dbRef = FirebaseDatabase.getInstance().getReference("Order")
        dbRef.child("order_"+userID).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    orderList.clear()
                    for(orderSnapshot in snapshot.children){
                        var orderModel = orderSnapshot.getValue(order_model::class.java)
                        orderList.add(orderModel!!)
                    }
                    val orderRecycler = order_recycler(orderList,userID,context)
                    recyclerView.adapter = orderRecycler
                }
                else{
                    orderList.clear()
                    val orderRecycler = order_recycler(orderList,userID,context)
                    recyclerView.adapter = orderRecycler
                    orderEmpty.setVisibility(View.VISIBLE)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        Log.d("Cart",""+orderList)
    }
}