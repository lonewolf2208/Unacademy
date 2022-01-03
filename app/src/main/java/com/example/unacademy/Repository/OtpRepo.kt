package com.example.unacademy.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Ui.Auth.Otp
import com.example.unacademy.api.Api
import com.example.unacademy.models.Message
import com.example.unacademy.models.SignUpDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class OtpRepo(private var Api:Api) {
    private val OtpLiveData = MutableLiveData<Response<ResponseBody>>()

    val OtpResponse: LiveData<Response<ResponseBody>>
        get() = OtpLiveData
    fun OtpApi(email:String,otp:String)
    {
        val result = Api.Otp(email,otp)
        OtpLiveData.postValue(Response.Loading())
        result.enqueue(object : Callback<Message?> {
            override fun onResponse(
                call: Call<Message?>,
                response: retrofit2.Response<Message?>
            ) {
                if(response.code()==202)
                {
                    OtpLiveData.postValue(Response.Success())
                }
                else if(response.code()==401)
                {
                    OtpLiveData.postValue(Response.Error("Otp Doesn't match"))
                }
                else if(response.code()==400)
                {
                    OtpLiveData.postValue(Response.Error("Otp Expired !! Please resend OTP"))
                }
                else
                {
                    OtpLiveData.postValue(Response.Error("Something went Wrong ! Please Try Again"))
                }
            }

            override fun onFailure(call: Call<Message?>, t: Throwable) {
               OtpLiveData.postValue(Response.Error("Something went wrong . Please try again !!"))
            }
        })
    }
}