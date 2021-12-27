package com.example.unacademy.models

//data class LoginDataClass(
//    val email: String,
//    val password:String,
//)
class LoginDataClass (email:String,password:String) {
    var email:String
    var password:String
    init {
        this.email=email
        this.password=password
    }
}