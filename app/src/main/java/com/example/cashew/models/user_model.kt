package com.example.cashew.models

data class user_model(var userID:String?="",
                      var userName: String?="",
                      var userEmail: String?="",
                      var userUname: String?="",
                      var userPwd: String?="",
                      var userDob: String?="",
                      var userCashewCoins:Int? = 0,
                      var userCashew:String? = "default",
                      var userType:String="user") {

}

data class current_user(
    var userID:String?="",
    var userName: String?="",
    var userEmail: String?="",
    var userUname: String?="",
    var userCashewCoins:Int? = 0,
    var userCashew:String? = "default") {

}