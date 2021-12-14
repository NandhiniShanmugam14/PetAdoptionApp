package com.example.petadoptionapplication.data

import com.example.petadoptionapplication.data.LoginHistory.loginEntriesList
import com.example.petadoptionapplication.data.others.usersList
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

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 048ba226-8087-4fb0-ac22-f52cb2c135b7"
    )
    @GET("users/me/loginHistory")
    suspend fun getLoginEntries():loginEntriesList

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 048ba226-8087-4fb0-ac22-f52cb2c135b7"
    )
    @GET("users")
    suspend fun getUsers(): usersList

}

