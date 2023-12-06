package com.example.cashew.objects

import android.util.Log
import com.example.cashew.models.current_user
import com.example.cashew.models.user_model

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

public class User {
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    var currentUser = arrayListOf<current_user>()

    public fun getUserData(userUname:String) {

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.reference.child("Users")

        dbRef.orderByChild("userUname").equalTo(userUname).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                         var userData = userSnapshot.getValue(user_model::class.java)
                        Log.d("Current User: ",""+userData)

//                        var User = current_user {
//                            userData!!.userID.toString();
//                            userData!!.userName.toString();
//                            userData!!.userEmail.toString();
//                            userData!!.userUname.toString();
//                            userData!!.userCashewCoins?.toInt();
//                            userData!!.userCashew.toString()
//                        }
//
//                        currentUser.add(User)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}