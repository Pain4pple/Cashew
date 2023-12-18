package com.example.cashew

import CartAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.cashew.models.cart_model
import com.example.cashew.models.order_model
import com.example.cashew.models.user_model
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.Date


class orderHistory_dialog(orderModel:order_model) : DialogFragment() {
    private lateinit var viewLayout: View
    private lateinit var listView: ListView
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var dbRef: DatabaseReference
    private lateinit var dbRef2: DatabaseReference
    private lateinit var myadapter: CartAdapter
    private var orderModel: order_model = orderModel
    private var cartList: ArrayList<cart_model> = ArrayList()
    private lateinit var closeBtn : ImageButton
    private lateinit var sh : SharedPreferences
    lateinit var totalAmount : TextView
    lateinit var orderDateTime : TextView
    public var totalCart:Float = 0f




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater.inflate(R.layout.activity_orderdetails_history, container, false)
        listView = viewLayout.findViewById(R.id.orderDetailsListView)
        closeBtn = viewLayout.findViewById(R.id.cancelBtn2)
        totalAmount = viewLayout.findViewById(R.id.TotalAmount)
        orderDateTime = viewLayout.findViewById(R.id.orderDateTime)

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.getReference("Cart")





        sh = requireActivity().applicationContext!!.getSharedPreferences("currentUserDetails", AppCompatActivity.MODE_PRIVATE)
        val orderSh = requireActivity().applicationContext!!.getSharedPreferences("orderDetails", AppCompatActivity.MODE_PRIVATE)
        val userID = sh.getString("ID","").toString()
        val orderWay = orderSh.getString("OrderWay","").toString()

        dbRef2 = firebaseDatabase.getReference("Order").child("order_"+userID)

        //Populate time
        orderDateTime.text = "Date and Time: "+ orderModel.orderDate

        //Populate Product List
        myadapter = CartAdapter(requireContext(), orderModel.orderProducts!!)
        listView.adapter = myadapter

        //Populate Order Total
        totalAmount.text = "Total: "+ orderModel.totalAmount


        val cancelBtn: ImageButton = viewLayout.findViewById(R.id.cancelBtn2)
        cancelBtn.setOnClickListener {
            dismiss()

        }
        closeBtn.setOnClickListener() {
            dismiss()
        }

        // Return the inflated layout
        return viewLayout
    }

}
