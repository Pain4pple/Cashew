package com.example.cashew.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class cart_model(var productID:String?=null,
                      var productName: String?=null,
                      var productImage:Int?=null,
                      var productPrice: Int?=null,
                      var productQty: Int=0,
                      var totalPriceOf: Float? = 0f)


