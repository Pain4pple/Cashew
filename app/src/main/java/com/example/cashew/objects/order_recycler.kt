package com.example.cashew.objects

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashew.R
import com.example.cashew.models.order_model
import com.example.cashew.models.product_model
import com.google.firebase.database.DatabaseReference

class order_recycler (private val products: ArrayList<order_model>, private val userID:String, private val context: Context) :
    RecyclerView.Adapter<product_recycler.ViewHolder>() {
    private lateinit var dbRef: DatabaseReference

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView
        val productPrice: TextView
        val productImage: ImageView
        val addButton: ImageButton

        init {
            // Define click listener for the ViewHolder's View
            productName = view.findViewById(R.id.productName)
            productPrice = view.findViewById(R.id.productPrice)
            productImage = view.findViewById(R.id.productImage)
            addButton = view.findViewById(R.id.addToCart)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): product_recycler.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: product_recycler.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}