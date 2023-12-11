package com.example.cashew.objects

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.cashew.R
import com.example.cashew.login_page
import com.example.cashew.models.cart_item
import com.example.cashew.models.cart_model
import com.example.cashew.models.product_model
import com.example.cashew.models.user_model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class product_recycler(private val products: ArrayList<product_model>,private val userID:String,private val context: Context) :
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
        dbRef = FirebaseDatabase.getInstance().getReference("Cart")

        viewHolder.addButton.setOnClickListener{
            Log.d("action","Added "+currentProduct.productName)
            val cart_item = cart_item(currentProduct.productID,currentProduct.productName,+1)
//            val cart_items=null
//            cart_items.add(cart_item)
            val cart_model = cart_model(userID,)
            dbRef.child("cartItems_"+userID).childByAutoID(cart_item).addOnSuccessListener {
                Toast.makeText(context, "Added "+currentProduct.productName,Toast.LENGTH_LONG).show()
            }.addOnFailureListener{
                Toast.makeText(context, "Failed to add",Toast.LENGTH_LONG).show()
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = products.size

}
