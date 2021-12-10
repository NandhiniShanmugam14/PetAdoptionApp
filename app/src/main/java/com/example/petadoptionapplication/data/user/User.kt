package com.example.petadoptionapplication.data.user

data class User(
    val id:Int,
    val token:String,
    val email:String,
    val memberSince:Long,
    val password:String
)
data class UserReq(
    val email: String?,
    val password: String?
)
