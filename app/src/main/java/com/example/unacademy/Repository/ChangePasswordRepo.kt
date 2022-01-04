package com.example.unacademy.Repository

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.api.Api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class ChangePasswordRepo(private val Api:Api)
{
    private val ChangeePasswordRepoLiveData = MutableLiveData<Response<String>>()

    val ChangePasswordResponse: LiveData<Response<String>>
        get() = ChangeePasswordRepoLiveData
    fun PasswordApi(email:String,password:String)
    {
        val result = Api.passwordChange(email,password)
        ChangeePasswordRepoLiveData.postValue(Response.Loading())
        result.enqueue(object : Callback<com.example.unacademy.models.Message?> {
            override fun onResponse(
                call: Call<com.example.unacademy.models.Message?>,
                response: retrofit2.Response<com.example.unacademy.models.Message?>
            ) {
                if(response.isSuccessful)
                {
//                  GetTokenRepo(Api).getToken(email, password)
//                    var tokens=GetTokenRepo(Api).TokenResponse
                    ChangeePasswordRepoLiveData.postValue(Response.Success())
                }
                else
                {
                    ChangeePasswordRepoLiveData.postValue(Response.Error(response.message().toString()))
                }
            }

            override fun onFailure(call: Call<com.example.unacademy.models.Message?>, t: Throwable) {
                ChangeePasswordRepoLiveData.postValue(Response.Error("Something went wrong . Please try again !!"))
            }
        })

    }

}