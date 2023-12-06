package com.example.cashew.models

data class cart_model(var userID:String?="",
                      var cartItems:ArrayList<cart_items>?=null){

}

data class cart_items(var productID:String?="",
                      var productName: String?="",
                      var productImage: String?="",
                      var productPrice: String?="",
                      var productQty: Int?=1){}
