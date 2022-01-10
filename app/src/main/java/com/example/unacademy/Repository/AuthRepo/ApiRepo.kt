package com.example.unacademy.Repository.AuthRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.AuthModels.LoginDataClass
import retrofit2.Call
import retrofit2.Callback
import java.lang.Exception

class ApiRepo(private var Api:Api) {

    private val ApiLiveData = MutableLiveData<Response<LoginDataClass>>()

    val ApiResponse:LiveData<Response<LoginDataClass>>
    get() = ApiLiveData
   fun getLoginApi(email:String,password:String)
    {
        val result = Api.LoginApi(email = email,password)
        ApiLiveData.postValue(Response.Loading())
        try {
          result.enqueue(object : Callback<LoginDataClass?> {
              override fun onResponse(
                  call: Call<LoginDataClass?>,
                  response: retrofit2.Response<LoginDataClass?>
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
              override fun onFailure(call: Call<LoginDataClass?>, t: Throwable)
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