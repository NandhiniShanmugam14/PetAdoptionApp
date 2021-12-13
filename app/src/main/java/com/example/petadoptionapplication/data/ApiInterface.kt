package com.example.petadoptionapplication.data

import com.example.petadoptionapplication.data.pets.PetList
import com.example.petadoptionapplication.data.user.User
import com.example.petadoptionapplication.data.user.UserReq
import okhttp3.ResponseBody

import retrofit2.Call

import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("login")
     fun userLogin(@Body user: UserReq): Call<User>

    @Headers("Content-Type: application/json")
    @POST("register")
    fun userRegister(@Body user: UserReq):Call<ResponseBody>

    //Pets
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 048ba226-8087-4fb0-ac22-f52cb2c135b7"
    )
    @GET("pets")
    suspend fun getPets(): PetList

}

