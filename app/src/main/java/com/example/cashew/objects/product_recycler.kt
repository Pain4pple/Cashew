package com.example.cashew.objects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.cashew.R
import com.example.cashew.models.product_model


class product_recycler(private val products: ArrayList<product_model>) :
    RecyclerView.Adapter<product_recycler.ViewHolder>() {

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

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.product_item_card, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentProduct = products[position]
        viewHolder.productName.text = currentProduct.productName
        viewHolder.productPrice.text = "₱"+currentProduct.productPrice.toString()
        viewHolder.productImage.setImageResource(currentProduct.productImage)

        viewHolder.addButton.setOnClickListener{

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = products.size

}
