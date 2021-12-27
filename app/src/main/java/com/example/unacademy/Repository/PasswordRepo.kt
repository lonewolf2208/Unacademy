package com.example.unacademy.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.api.Api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class PasswordRepo(private var Api:Api) {
    private val PasswordRepoLiveData = MutableLiveData<Response<ResponseBody>>()

    val PasswordResponse: LiveData<Response<ResponseBody>>
        get() = PasswordRepoLiveData
    fun PasswordApi(name:String,email:String,password:String)
    {
        val result = Api.Password(name,email,password)
        PasswordRepoLiveData.postValue(Response.Loading())
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if(response.isSuccessful)
                {
//                  GetTokenRepo(Api).getToken(email, password)
//                    var tokens=GetTokenRepo(Api).TokenResponse
                    PasswordRepoLiveData.postValue(Response.Success())
                }
                else if(response.code()==401)
                {
                    PasswordRepoLiveData.postValue(Response.Error("User Already Exists"))
                }
                else
                {
                    PasswordRepoLiveData.postValue(Response.Error("Something went wring !! Please try again"))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                PasswordRepoLiveData.postValue(Response.Error(t.message))
            }
        })

    }
}