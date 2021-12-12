package com.example.petadoptionapplication.data

import com.example.petadoptionapplication.data.user.User
import com.example.petadoptionapplication.data.user.UserReq

import retrofit2.Call

import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("login")
     fun userLogin(@Body user: UserReq): Call<User>

    @Headers("Content-Type: application/json")
    @POST("register")
    fun userRegister(@Body user: UserReq):Call<String>

}

