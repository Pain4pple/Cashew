package com.example.cashew.objects

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.cashew.R
import com.example.cashew.cart_page
import com.example.cashew.models.cart_model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class cart_recycler(private val cartItem: ArrayList<cart_model>, private val userID:String, private val context: Context) :
    RecyclerView.Adapter<cart_recycler.ViewHolder>() {
    private lateinit var dbRef: DatabaseReference

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView
        val itemPrice: TextView
        val itemQty: TextView
        val itemImage: ImageView
        val removeButton: Button
        val addButton: ImageButton
        val minusButton: ImageButton

        init {
            // Define click listener for the ViewHolder's View
            itemName = view.findViewById(R.id.itemName)
            itemPrice = view.findViewById(R.id.itemPrice)
            itemQty = view.findViewById(R.id.itemQty)
            itemImage = view.findViewById(R.id.itemImage)
            addButton = view.findViewById(R.id.addButton)
            removeButton = view.findViewById(R.id.removeButton)
            minusButton = view.findViewById(R.id.minusButton)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_layoutfororder, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentItem = cartItem[position]
        viewHolder.itemName.text = currentItem.productName
        viewHolder.itemPrice.text = "₱"+currentItem.productPrice.toString()
        viewHolder.itemQty.text = currentItem.productQty.toString()
        viewHolder.itemImage.setImageResource(currentItem.productImage!!)
        dbRef = FirebaseDatabase.getInstance().getReference("Cart").child("cartItems_"+userID)

        viewHolder.minusButton.setOnClickListener{
            val cartModel = cart_model()
            dbRef.child(currentItem.productID!!).addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val cartModel = snapshot.getValue(cart_model::class.java)
                        if(cartModel!!.productQty==1){
                            dbRef.child(currentItem.productID!!).removeValue().addOnSuccessListener {
                                Toast.makeText(context,"Removed "+currentItem.productName,Toast.LENGTH_SHORT).show()
                                val cartObject = cart_page()
                                if((cartObject.totalCart - currentItem!!.productPrice!!).toInt() <0){
                                    cartObject.totalCart -= currentItem!!.productPrice!!
                                }
                                else{
                                    cartObject.totalCart = 0f
                                }

                            }.addOnCanceledListener {
                                Toast.makeText(context,"Failed to remove "+currentItem.productName,Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            val updateData:MutableMap<String,Any> = HashMap()
                            cartModel!!.productQty = cartModel!!.productQty-1
                            updateData["productQty"] = cartModel!!.productQty
                            updateData["totalPriceOf"] = (cartModel!!.productQty * cartModel!!.productPrice!!).toFloat()
                            dbRef.child(currentItem.productID!!).updateChildren(updateData).addOnSuccessListener {
                                Toast.makeText(context,"Removed 1 "+currentItem.productName,Toast.LENGTH_SHORT).show()
                                val cartObject = cart_page()
                                if((cartObject.totalCart - currentItem!!.productPrice!!).toInt() <0){
                                    cartObject.totalCart -= currentItem!!.productPrice!!
                                }
                                else{
                                    cartObject.totalCart = 0f
                                }

                            }.addOnCanceledListener {
                                Toast.makeText(context,"Failed to remove "+currentItem.productName,Toast.LENGTH_SHORT).show()
                                viewHolder.itemQty.text = cartModel!!.productQty.toString()
                            }
                        }
                    }
                    else{
                            Toast.makeText(context,"Failed to remove "+currentItem.productName,Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("action","Error: "+error)
                }
            })
            /*var cartObject = cart_page()
            cartObject.getCart(userID,context)*/
            notifyItemChanged(cartItem.indexOf(currentItem));
        }

        viewHolder.removeButton.setOnClickListener{
            val cart_model = cart_model()
            dbRef.child(currentItem.productID!!).addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val cartModel = snapshot.getValue(com.example.cashew.models.cart_model::class.java)
                        if(cartModel!!.productQty>=0){
                            dbRef.child(currentItem.productID!!).removeValue().addOnSuccessListener {
                                Toast.makeText(context,"Removed "+currentItem.productName,Toast.LENGTH_SHORT).show()
                                val cartObject = cart_page()
                                if((cartObject.totalCart - currentItem!!.productPrice!!).toInt() <0){
                                    cartObject.totalCart -= currentItem!!.productPrice!!
                                }
                                else{
                                    cartObject.totalCart = 0f
                                }

                            }.addOnCanceledListener {
                                Toast.makeText(context,"Failed to remove "+currentItem.productName,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else{
                        Toast.makeText(context,"Failed to remove "+currentItem.productName,Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("action","Error: "+error)
                }
            })
            notifyItemChanged(cartItem.indexOf(currentItem));

        }
        viewHolder.addButton.setOnClickListener{
//            val cart_model = cart_model()
            dbRef.child(currentItem.productID!!).addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val cartModel = snapshot.getValue(cart_model::class.java)
                        val updateData:MutableMap<String,Any> = HashMap()
                        cartModel!!.productQty = cartModel!!.productQty+1
                        updateData["productQty"] = cartModel!!.productQty
                        updateData["totalPriceOf"] = (cartModel!!.productQty * cartModel!!.productPrice!!).toFloat()
                        dbRef.child(currentItem.productID!!).updateChildren(updateData).addOnSuccessListener {
                            Toast.makeText(context,"Added "+currentItem.productName,Toast.LENGTH_SHORT).show()
                            viewHolder.itemQty.text = cartModel!!.productQty.toString()
                            val cartObject = cart_page()
                            cartObject.totalCart += cartModel!!.productPrice!!
                        }.addOnCanceledListener {
                            Toast.makeText(context,"Failed to add "+currentItem.productName,Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("action","Error: "+error)
                }
            })
           /* var cartObject = cart_page()
            cartObject.getCart(userID,context)*/
            notifyItemChanged(cartItem.indexOf(currentItem));

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = cartItem.size

}
