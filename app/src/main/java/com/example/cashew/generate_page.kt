package com.example.cashew


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView

import android.widget.TextView

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cashew.models.cart_model
import com.example.cashew.models.order_model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.time.LocalDate

class generate_page : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var dbRef2: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var sh : SharedPreferences
    private var orderList : ArrayList<order_model> = ArrayList()
    lateinit var orderIDText : TextView
    lateinit var currentID : String
    lateinit var cancelButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_page)


        // INITIALIZATIONS
        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.getReference("Cart")
        sh = getSharedPreferences("currentUserDetails", AppCompatActivity.MODE_PRIVATE)
        orderIDText = findViewById(R.id.orderIDLabel)
        val userID = sh.getString("ID","").toString()
        cancelButton = findViewById(R.id.cancelOrderlBtn)
        var orderID = ""

        val extras = intent.extras
        if (extras != null){
            orderID = extras!!.getString("OrderID").toString()
        }
        // GENERATE QR CODE
//      getOrderDetails(userID)
//      getOrderData(userID,orderWay)
        getOrderData(userID, orderID)


        sh = getSharedPreferences("currentUserDetails", AppCompatActivity.MODE_PRIVATE)
        val orderSh = getSharedPreferences("orderDetails", MODE_PRIVATE)
        val orderWay = orderSh.getString("OrderWay","").toString()
        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.getReference("Cart")
        dbRef2 = firebaseDatabase.getReference("Order").child("order_"+userID)



        //val order = order_model(orderID = "123456", orderDate = null)
        //val order = currentID
      //generateQRCode(order)

        // CANCEL OR DELETE ORDER
        cancelButton.setOnClickListener {
            // Call the function to cancel the order
            cancelOrder(userID, orderID)
        }
    }


    private fun generateQRCode(order: order_model) {

        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap =
            barcodeEncoder.encodeBitmap(order.toString(), BarcodeFormat.QR_CODE, 400, 400)
        val imageView: ImageView = findViewById(R.id.qrCodeImageView)
        imageView.setImageBitmap(bitmap)
    }

 /*   private fun getOrderData(userID: String,orderWay:String) {
        var cartModel :ArrayList<cart_model> = ArrayList()
        var total :Float? = 0f
        dbRef.child("cartItems_"+userID).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    cartModel.clear()
                    total = 0f
                    for (snapshot in dataSnapshot.children) {
                        val cartItem = snapshot.getValue(cart_model::class.java)
                        if (cartItem != null) {
                            cartModel.add(cartItem!!)
                            total = total!! + cartItem.totalPriceOf!!
                        }

                    }

                    val orderID = "order_"+dbRef2.push().key!!
                    var orderModel = order_model(orderID,userID,cartModel, LocalDate.now().toString(),total,"Cash",orderWay)

                    generateQRCode(orderModel)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("OrderSummaryDialog", "Error retrieving data: ${databaseError.message}")
            }
        })

    }*/

    private fun getOrderData(userID:String,orderID: String) {
        dbRef = FirebaseDatabase.getInstance().getReference("Order")
        dbRef.child("order_"+userID).child(orderID).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    val orderModel = dataSnapshot.getValue(order_model::class.java)
                    generateQRCode(orderModel!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("OrderSummaryDialog", "Error retrieving data: ${databaseError.message}")
            }
        })

    }

/*    private fun getOrderDetails(userID:String) {
        dbRef = FirebaseDatabase.getInstance().getReference("Order")
        dbRef.child("order_"+userID).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    orderList.clear()
                     currentID = "";

                    for(orderSnapshot in dataSnapshot.children){

                        var currentOrder = orderSnapshot.getValue(order_model::class.java)
                        if (currentOrder != null) {

                            orderList.add(currentOrder!!)
                            currentID = currentOrder.orderID ?: ""

                        }

                    }
                   orderIDText.text = "ORDERID:\n"+currentID.toString()
                    // myadapter.notifyDataSetChanged()
                    generateQRCode(order_model(orderID = currentID, orderDate = null))
                    Log.i("OrderSummaryDialog", "Error retrieving data: $currentID")
                    Log.i("GeneratePage", "User retrieving data: $userID")

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("OrderSummaryDialog", "Error retrieving data: ${databaseError.message}")
            }
        })
    }*/

    private fun cancelOrder(userID : String, orderID : String) {
        val currentOrder = firebaseDatabase.getReference("Order").child("order_$userID").child(orderID)
        //val currentCart = dbRef.child("cartItems_$userID")

        currentOrder.removeValue().addOnSuccessListener {
            Toast.makeText(this, "Order cancelled", Toast.LENGTH_SHORT).show()
        }
        val intent = Intent(this,cart_page::class.java)
        startActivity(intent)

    }



}