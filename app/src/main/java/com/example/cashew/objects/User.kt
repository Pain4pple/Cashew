package com.example.cashew.objects

import android.util.Log
import com.example.cashew.models.user_model

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

public class User {
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    public fun getUserData(userUname:String): user_model? {

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.reference.child("Users")

        var userData: user_model? = null;
        dbRef.orderByChild("userUname").equalTo(userUname).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                         userData = userSnapshot.getValue(user_model::class.java)
                        Log.d("Current User: ",""+userData)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        return userData
    }
}