package com.example.unacademy.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    private val client = OkHttpClient.Builder().build()

    fun getInstance():Retrofit{
        return  Retrofit.Builder()
            .baseUrl("https://unacademy-software-incubator.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    fun init():Api
    {
        return getInstance().create(Api::class.java)
    }


}