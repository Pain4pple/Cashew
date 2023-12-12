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
import com.example.cashew.models.cart_model
import com.example.cashew.models.product_model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


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
        dbRef = FirebaseDatabase.getInstance().getReference("Cart").child("cartItems_"+userID)

        viewHolder.addButton.setOnClickListener{
            Log.d("action","Added "+currentProduct.productName)

/*            dbRef
            val cart_item = cart_item(currentProduct.productID,currentProduct.productName,+1)
            val cart_items=null
            cart_items.add(cart_item)*/
            val cart_model = cart_model()
            dbRef.child(currentProduct.productID!!).addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val cartModel = snapshot.getValue(cart_model::class.java)
                        val updateData:MutableMap<String,Any> = HashMap()
                        cartModel!!.productQty = cartModel!!.productQty+1
                        updateData["productQty"] = cartModel!!.productQty
                        updateData["totalPriceOf"] = (cartModel!!.productQty * cartModel!!.productPrice!!).toFloat()
                        dbRef.child(currentProduct.productID!!).updateChildren(updateData).addOnSuccessListener {
                            Toast.makeText(context,"Added "+currentProduct.productName,Toast.LENGTH_SHORT).show()
                        }.addOnCanceledListener {
                            Toast.makeText(context,"Failed to add "+currentProduct.productName,Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        val cartModel = cart_model()
                        cartModel.productID = currentProduct.productID
                        cartModel.productName = currentProduct.productName
                        cartModel.productImage = currentProduct.productImage
                        cartModel.productPrice = currentProduct.productPrice
                        cartModel.productQty = 1
                        cartModel.totalPriceOf = currentProduct.productPrice!!.toFloat()

                        dbRef.child(currentProduct.productID!!).setValue(cartModel).addOnSuccessListener {
                            Toast.makeText(context,"Added "+currentProduct.productName,Toast.LENGTH_SHORT).show()

                        }.addOnCanceledListener {
                            Toast.makeText(context,"Failed to add "+currentProduct.productName,Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("action","Error: "+error)
                }
            })
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = products.size

}
