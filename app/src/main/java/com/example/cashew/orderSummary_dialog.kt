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
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.Date


class orderSummary_dialog : DialogFragment() {
    private lateinit var viewLayout: View
    private lateinit var listView: ListView
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var dbRef: DatabaseReference
    private lateinit var dbRef2: DatabaseReference
    private lateinit var myadapter: CartAdapter
    private var cartList: ArrayList<cart_model> = ArrayList()
    private lateinit var payCash : Button
    private lateinit var sh : SharedPreferences
    lateinit var totalAmount : TextView
    lateinit var orderDateTime : TextView
    public var totalCart:Float = 0f




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater.inflate(R.layout.activity_orderdetails, container, false)
        listView = viewLayout.findViewById(R.id.orderDetailsListView)
        payCash = viewLayout.findViewById(R.id.cashButton)
        totalAmount = viewLayout.findViewById(R.id.TotalAmount)
        orderDateTime = viewLayout.findViewById(R.id.orderDateTime)

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.getReference("Cart")
        myadapter = CartAdapter(requireContext(), cartList)
        listView.adapter = myadapter




        sh = requireActivity().applicationContext!!.getSharedPreferences("currentUserDetails", AppCompatActivity.MODE_PRIVATE)
        val orderSh = requireActivity().applicationContext!!.getSharedPreferences("orderDetails", AppCompatActivity.MODE_PRIVATE)
        val userID = sh.getString("ID","").toString()
        val orderWay = orderSh.getString("OrderWay","").toString()

        dbRef2 = firebaseDatabase.getReference("Order").child("order_"+userID)

        payCash.setOnClickListener() {
            var intent = Intent(context, generate_page::class.java)
            startActivity(intent)

        }

       /* payCash.setOnClickListener() {
            saveOrderData(userID,orderWay)
            var intent = Intent(context, generate_page::class.java)
            startActivity(intent)


        }*/

        // Call function to populate ListView
        getCartDetails(userID)

        //Populate time
        orderDateTime.text = "Date and Time: "+ Date()

        val cancelBtn: ImageButton = viewLayout.findViewById(R.id.cancelBtn2)
        cancelBtn.setOnClickListener {
            // Close the current activity
            dismiss()

        }

        // Return the inflated layout
        return viewLayout
    }

    private fun saveOrderData(userID: String,orderWay:String) {
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
                    var orderModel = order_model(orderID,userID,cartModel, now().toString(),total,"Cash",orderWay)

                    dbRef2.child(orderID).setValue(orderModel).addOnSuccessListener{
                        //if successful, toast
                        Toast.makeText(context, "Completed order", Toast.LENGTH_LONG).show()
                       /* val intent = Intent(context,products_page::class.java)
                        startActivity(intent)

                        */

                    }.addOnFailureListener{
                        Toast.makeText(context, "Order Failed", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("OrderSummaryDialog", "Error retrieving data: ${databaseError.message}")
            }
        })


    }

    private fun getCartDetails(userID:String) {
        dbRef.child("cartItems_"+userID).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    cartList.clear()
                    totalCart = 0f
                    for (snapshot in dataSnapshot.children) {

                        val cartItem = snapshot.getValue(cart_model::class.java)
                        if (cartItem != null) {
                            cartList.add(cartItem)
                            totalCart += cartItem.totalPriceOf!!
                        }

                    }
                    totalAmount.text = "Total:\n₱"+totalCart.toString()
                    myadapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("OrderSummaryDialog", "Error retrieving data: ${databaseError.message}")
            }
        })
    }

}
