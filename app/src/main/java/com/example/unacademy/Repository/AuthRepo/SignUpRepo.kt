package com.example.unacademy.Repository.AuthRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class SignUpRepo(private var Api:Api) {
    private val SignUpLiveData = MutableLiveData<Response<ResponseBody>>()

    val SignUpResponse: LiveData<Response<ResponseBody>>
        get() = SignUpLiveData
    fun SignUpApi(email: String,name:String)
    {
        val result = Api.SignUpApi(email,name)
        SignUpLiveData.postValue(Response.Loading())
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if(response.isSuccessful)
                {
                    SignUpLiveData.postValue(Response.Success())
                }
                else if(response.code()==302)
                {
                    SignUpLiveData.postValue(Response.Success())
                }
                else
                {
                    SignUpLiveData.postValue(Response.Error("Something went wrong . Please try again !!"))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                SignUpLiveData.postValue(Response.Error("Something went wrong . Please try again !!"))
            }
        })

    }
}