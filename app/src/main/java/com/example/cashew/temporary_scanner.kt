package com.example.cashew

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.cashew.models.cart_model
import com.example.cashew.models.order_model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate

class temporary_scanner : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var dbRef2: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var sh : SharedPreferences
    private var orderList : ArrayList<order_model> = ArrayList()
    lateinit var confirmPayment : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner_page)

        sh = getSharedPreferences("currentUserDetails", AppCompatActivity.MODE_PRIVATE)
        val orderSh = getSharedPreferences("orderDetails", MODE_PRIVATE)

        val userID = sh.getString("ID","").toString()
        val orderWay = orderSh.getString("OrderWay","").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.getReference("Cart")
        dbRef2 = firebaseDatabase.getReference("Order").child("order_"+userID)

        confirmPayment = findViewById(R.id.confirmPayment)

        confirmPayment.setOnClickListener {
//            saveOrderData()
//            sendNotification()
        }
    }

/*    private fun saveOrderData(orderModel:order_model) {

                    dbRef2.child(orderID).setValue(orderModel).addOnSuccessListener {
                        //if successful, toast
                        Toast.makeText(applicationContext, "Completed order", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(applicationContext, products_page::class.java)
                        startActivity(intent)

                    }.addOnFailureListener {
                        Toast.makeText(applicationContext, "Order Failed", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("OrderSummaryDialog", "Error retrieving data: ${databaseError.message}")
            }
        })

    }*/
}