package com.example.unacademy.Repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Ui.Auth.LogIn
import com.example.unacademy.api.Api
import com.example.unacademy.models.LoginDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import java.lang.Exception

class ApiRepo(private var Api:Api) {

    private val ApiLiveData = MutableLiveData<Response<ResponseBody>>()

    val ApiResponse:LiveData<Response<ResponseBody>>
    get() = ApiLiveData
   fun getLoginApi(email:String,password:String)
    {
        val result = Api.LoginApi(email = email,password)
        ApiLiveData.postValue(Response.Loading())
        try {
          result.enqueue(object : Callback<ResponseBody?> {
              override fun onResponse(
                  call: Call<ResponseBody?>,
                  response: retrofit2.Response<ResponseBody?>
              ) {
                  if(response.isSuccessful) {
                      ApiLiveData.postValue(Response.Success(response.body()))
                  }
                  else if(response.code()==406)
                  {
                      ApiLiveData.postValue(Response.Error("User Not Registered"))
                  }
                  else if(response.code()==401)
                  {
                      ApiLiveData.postValue(Response.Error("Incorrect Password"))
                  }
                  else
                  {
                      ApiLiveData.postValue(Response.Error("Something went wrong!!.Please Try Again!"))
                  }
              }
              override fun onFailure(call: Call<ResponseBody?>, t: Throwable)
              {
                  ApiLiveData.postValue(Response.Error("Something went wrong!!.Please Try Again!"))
              }
          })
        }
        catch (e:Exception)
        {
            ApiLiveData.postValue(Response.Error("Something went wrong . Please try again !!"))
        }

    }


}