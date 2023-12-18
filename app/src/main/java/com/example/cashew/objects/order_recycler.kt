package com.example.cashew.objects

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cashew.R
import com.example.cashew.models.order_model
import com.example.cashew.orderHistory_dialog
import com.example.cashew.orderhistory_page
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class order_recycler (private val orders: ArrayList<order_model>, private val userID:String, private val context: Context) :
    RecyclerView.Adapter<order_recycler.ViewHolder>() {
    private lateinit var dbRef: DatabaseReference

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderIDlabel: TextView
        val datetime: TextView
        val totalprice: TextView
        val vieworderdetails: Button

        init {
            // Define click listener for the ViewHolder's View
            orderIDlabel = view.findViewById(R.id.orderIDlabel)
            datetime = view.findViewById(R.id.datetime)
            totalprice = view.findViewById(R.id.totalprice)
            vieworderdetails = view.findViewById(R.id.vieworderdetails)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): order_recycler.ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_layoutfororderhistory, parent, false)

        return order_recycler.ViewHolder(view)
    }
    override fun onBindViewHolder(holder: order_recycler.ViewHolder, position: Int) {
        val currentOrder = orders[position]
        val counter = 1 + position
        holder.orderIDlabel.text = "Order ID: Order_"+counter
        holder.datetime.text = currentOrder.orderDate
        holder.totalprice.text = "Total Price:\n₱"+currentOrder.totalAmount.toString()
//        holder.vieworderdetails.setImageResource(currentItem.productImage!!)
//        dbRef = FirebaseDatabase.getInstance().getReference("Cart").child("cartItems_"+userID)
        
        holder.vieworderdetails.setOnClickListener { 
            getOrderDetails(currentOrder.orderID,currentOrder.userID)
        }
    }

    fun showDialog(orderModel:order_model) {
        val fragmentManager = (context as FragmentActivity).supportFragmentManager
        val newFragment = orderHistory_dialog(orderModel)
        newFragment.show(fragmentManager, "orderHistory_dialog")

    }

    private fun getOrderDetails(orderID: String?, userID: String?) {
        dbRef = FirebaseDatabase.getInstance().getReference("Order")
        dbRef.child("order_"+userID).child(orderID!!).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    val orderModel = dataSnapshot.getValue(order_model::class.java)
                    if (orderModel != null) {
                        showDialog(orderModel)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("OrderSummaryDialog", "Error retrieving data: ${databaseError.message}")
            }
        })
    }

    override fun getItemCount() = orders.size


}