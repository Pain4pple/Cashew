package com.example.cashew.models

data class cart_model(var userID:String?="",
                      var cartItems:ArrayList<cart_item>?=null){

}

data class cart_item(var productID:String?="",
                      var productName: String?="",
                      var productQty: Int?=1,

                      //var productImage: Int?=0,
                     // var productPrice: String?=""
){}
