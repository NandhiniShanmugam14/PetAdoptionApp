package com.example.petadoptionapplication.data

import com.example.petadoptionapplication.data.LoginHistory.loginEntriesList
import com.example.petadoptionapplication.data.others.usersList
import com.example.petadoptionapplication.data.pets.PetInterests
import com.example.petadoptionapplication.data.pets.PetList
import com.example.petadoptionapplication.data.pets.SendPet
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
    @Headers("Content-Type: application/json")
    @GET("pets")
    fun getPets(@HeaderMap headerMap:MutableMap<String,String>): Call<PetList>

    //Get PetInterests
    @Headers("Content-Type: application/json")
    @GET("users/me/petInterests")
    fun getPetInterests(@HeaderMap headerMap:MutableMap<String,String>): Call<PetInterests>

    //DeletePetInterest
    @DELETE("users/me/petInterests/{interestId}")
    fun deleteInterest(@Path("interestId") interestId: Int):Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @GET("users/me/loginHistory")
    suspend fun getLoginEntries(@HeaderMap headerMap:MutableMap<String,String>): loginEntriesList

    @Headers("Content-Type: application/json")
    @GET("users")
    suspend fun getUsers(@HeaderMap headerMap:MutableMap<String,String>): usersList

    @Headers("Content-Type: application/json")
    @POST("users/me/petInterests")
    fun postInterest(@Body send:SendPet,@HeaderMap headerMap: MutableMap<String, String>):Call<ResponseBody>


}

