package com.example.cashew

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.cashew.R
import com.example.cashew.models.cart_model
import com.example.cashew.models.order_model
import com.example.cashew.objects.cart_recycler
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class generate_page : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var sh : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_page)

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.getReference("Cart")
        sh = getSharedPreferences("currentUserDetails", AppCompatActivity.MODE_PRIVATE)


        val order = order_model(orderID = "123456", orderDate = null)
        generateQRCode(order)
    }

    private fun generateQRCode(order: order_model) {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap =
            barcodeEncoder.encodeBitmap(order.orderID, BarcodeFormat.QR_CODE, 400, 400)
        val imageView: ImageView = findViewById(R.id.qrCodeImageView)
        imageView.setImageBitmap(bitmap)
    }

}