package com.example.cashew

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.cashew.models.order_model
import com.example.cashew.models.user_model
import com.example.cashew.objects.qr_list_adapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator


class scanner_page : AppCompatActivity() {
    lateinit var scan_btn: Button
    lateinit var close : ImageButton
    lateinit var confirm : Button
    lateinit var textView: TextView
    lateinit var orderDetailsText: TextView
    lateinit var orderIDText : TextView
    lateinit var currentID : String
    var cashewCoins : Int = 0

    private lateinit var dbRef: DatabaseReference
    private lateinit var dbRef2: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var listView: ListView
    private lateinit var sh : SharedPreferences
    private var orderList : ArrayList<order_model> = ArrayList()

    private lateinit var listAdapter: qr_list_adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner_page)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        scan_btn = findViewById(R.id.scanner)
        confirm = findViewById(R.id.confirmPayment)
        close = findViewById(R.id.cancelBtn3)
        listView = findViewById(R.id.orderProductsListView)
        textView = findViewById(R.id.text)
        orderDetailsText = findViewById(R.id.textView4)


        // INITIALIZATIONS
        firebaseDatabase = FirebaseDatabase.getInstance()
        sh = getSharedPreferences("currentUserDetails", AppCompatActivity.MODE_PRIVATE)
        val userID = sh.getString("ID","").toString()


        scan_btn.setOnClickListener(View.OnClickListener {
            val intentIntegrator = IntentIntegrator(this@scanner_page)
            intentIntegrator.setOrientationLocked(false)
            intentIntegrator.setPrompt("Scan a QR Code")
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.initiateScan()
        })



        close.setOnClickListener(View.OnClickListener {
            //TO DO: device to device notification
            val intent = Intent(this, products_page::class.java)
            startActivity(intent)
            finish()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            val contents = intentResult.contents
            val splitContent = intentResult.contents.split(",").toTypedArray()
            if (contents != null) {
                getOrderData(splitContent[0],splitContent[1])
                Log.d("Details","orderID:${splitContent[0]} | userID:${splitContent[1]}")

                confirm.setOnClickListener(View.OnClickListener {
                    //TO DO: device to device notification
                    giveCoins(splitContent[1],cashewCoins)
                    val intent = Intent(this, scanner_page::class.java)
                    startActivity(intent)
                    finish()
                })
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }


    }

    private fun getOrderData(orderID:String,userID: String) {
        dbRef = FirebaseDatabase.getInstance().getReference("Order")
        dbRef.child("order_"+userID).child(orderID).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    val orderModel = dataSnapshot.getValue(order_model::class.java)
                    if (orderModel != null) {
                        currentID = orderModel.orderID ?: ""
                        cashewCoins = (orderModel.totalAmount?.times(.30))!!.toInt()
                        Log.d("Details","currentOrder: "+orderModel)

                        listAdapter = qr_list_adapter(applicationContext, orderModel.orderProducts!!)
                        listView.adapter = listAdapter
                        textView!!.text = "Total: "+orderModel.totalAmount.toString()
                        confirm.setVisibility(View.VISIBLE)
                        listView.setVisibility(View.VISIBLE)
                        orderDetailsText.setVisibility(View.VISIBLE)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("OrderSummaryDialog", "Error retrieving data: ${databaseError.message}")
            }
        })

    }

    private fun giveCoins(userID: String, cashewCoins: Int) {
        var coinsRef = firebaseDatabase.getReference("Users")
        coinsRef.child(userID).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val userDetails = snapshot.getValue(user_model::class.java)
//                    Log.d("OrderSummaryDialog", "$userDetails")

                    val newCoins = userDetails!!.userCashewCoins!! + cashewCoins
                    coinsRef.child(userID).child("userCashewCoins").setValue(newCoins).addOnSuccessListener {

                    }.addOnCanceledListener {
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("action","Error: "+error)
            }
        })
    }
}


/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            val contents = intentResult.contents
            if (contents != null) {
                val contentArray = contents.split("\n").toTypedArray()

                // Assuming you have an array of TextViews with IDs text1, text2, text3, ...
                val textViewIds =
                    arrayOf(R.id.text1, R.id.text2, R.id.text3 /* Add more IDs as needed */)

                for (i in contentArray.indices) {
                    if (i < textViewIds.size) {
                        val currentTextView = findViewById<TextView>(textViewIds[i])
                        currentTextView.text = contentArray[i]
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

 */
