package com.example.petadoptionapplication.data

import android.app.Application
import com.fasterxml.jackson.databind.ObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

class PetApplication:Application() {
    lateinit var api:ApiInterface
    override fun onCreate() {
        super.onCreate()
        api=httpPetService()
    }
    private fun httpPetService():ApiInterface
    {
        val retrofit=Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/doggeforlife-mobile/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiInterface::class.java)
    }
}