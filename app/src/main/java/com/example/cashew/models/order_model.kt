package com.example.cashew.models

import java.util.Date

data class order_model(var orderID:String?=null,
                       var userID: String?=null,
                       var orderProducts:ArrayList<cart_model>?=null,
                       var orderDate: String?=null,
                       var totalAmount: Float? = 0f,
                       var modeOfPayment:String?=null,
                       var orderWay:String?=null)
