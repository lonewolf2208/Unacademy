package com.example.unacademy.Repository

import android.media.session.MediaSession
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.api.Api
import com.example.unacademy.models.SignUpDataClass
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import java.lang.Exception

class GetTokenRepo(private var Api: Api) {
    companion object
    {
        var accessToken:String=""
    }
    private val TokenLiveData = MutableLiveData<Response<SignUpDataClass>>()

    val TokenResponse: LiveData<Response<SignUpDataClass>>
        get() = TokenLiveData

    fun getToken(email:String,password:String)
    {
        val result = Api.getToken(email,password)
        TokenLiveData.postValue(Response.Loading())
        try {
            result.enqueue(object : Callback<SignUpDataClass?> {
                override fun onResponse(
                    call: Call<SignUpDataClass?>,
                    response: retrofit2.Response<SignUpDataClass?>
                ) {
                    if(response.isSuccessful)
                    {
                        TokenLiveData.postValue(Response.Success(response.body()))

                        accessToken="Successfull"
                    }
                    else
                    {
                       TokenLiveData.postValue(Response.Error(response.message()))
                        accessToken="else"

                    }
                }
                override fun onFailure(call: Call<SignUpDataClass?>, t: Throwable)
                {
                    TokenLiveData.postValue(Response.Error("Something went wrong . Please try again !!"))
                    accessToken="OnFailure"
                }
            })
        }
        catch (e: Exception)
        {
            TokenLiveData.postValue(Response.Error("Something went wrong . Please try again !!"))
            accessToken="Catch"
        }
    }
}