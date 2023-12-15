package com.example.cashew.objects

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashew.R
import com.example.cashew.models.order_model
import com.example.cashew.models.product_model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
        val counter = position
        holder.orderIDlabel.text = "Order ID: Order_"+counter+1
        holder.datetime.text = currentOrder.orderDate
        holder.totalprice.text = "Total Price:\n₱"+currentOrder.totalAmount.toString()
//        holder.vieworderdetails.setImageResource(currentItem.productImage!!)
//        dbRef = FirebaseDatabase.getInstance().getReference("Cart").child("cartItems_"+userID)
    }

    override fun getItemCount() = orders.size


}