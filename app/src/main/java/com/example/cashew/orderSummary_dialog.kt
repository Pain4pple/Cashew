package com.example.cashew

import CartAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import com.example.cashew.models.cart_model
import com.google.firebase.database.*



class orderSummary_dialog : DialogFragment() {
    private lateinit var viewLayout: View
    private lateinit var listView: ListView
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var dbRef: DatabaseReference
    private lateinit var myadapter: CartAdapter
    //private var cartList: ArrayList<cart_model> = ArrayList()
    private var cartList = mutableListOf<cart_model>()
    private lateinit var payCash : Button



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater.inflate(R.layout.activity_orderdetails, container, false)
        listView = viewLayout.findViewById(R.id.orderDetailsListView)
        payCash = viewLayout.findViewById(R.id.cashButton)

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.getReference("Cart")

        myadapter = CartAdapter(requireContext(), cartList)
        listView.adapter = myadapter

        payCash.setOnClickListener() {
            var intent = Intent(context, generate_page::class.java)
            startActivity(intent)
            dismiss()
        }


        //retrieveCartDetails()

        // Return the inflated layout
        return viewLayout
    }

    private fun retrieveCartDetails() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                cartList.clear()

                for (snapshot in dataSnapshot.children) {
                    val cartItem = snapshot.getValue(cart_model::class.java)
                    if (cartItem != null) {
                        cartList.add(cartItem!!)
                    }
                }

                myadapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("OrderSummaryDialog", "Error retrieving data: ${databaseError.message}")
            }
        })
    }

}
