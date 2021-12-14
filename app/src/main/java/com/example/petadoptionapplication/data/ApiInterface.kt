package com.example.petadoptionapplication.data

import com.example.petadoptionapplication.data.pets.PetInterests
import com.example.petadoptionapplication.data.pets.PetList
import com.example.petadoptionapplication.data.user.User
import com.example.petadoptionapplication.data.user.UserReq
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.Response

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
    fun getPets(): Call<PetList>

    //Get PetInterests
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 048ba226-8087-4fb0-ac22-f52cb2c135b7"
    )
    @GET("users/me/petInterests")
    fun getPetInterests(): Call<PetInterests>

    //DeletePetInterest
    @DELETE("users/me/petInterests/{interestId}")
    fun deleteInterest(@Path("interestId") interestId: Int):Call<ResponseBody>

}

