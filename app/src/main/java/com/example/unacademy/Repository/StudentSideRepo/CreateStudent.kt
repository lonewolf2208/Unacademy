package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class CreateStudent(val Api:Api) {
    private val createStudentLiveData = MutableLiveData<Response<ResponseBody>>()
    fun createStudentApi(
       name:String,
       gender:String,
       birth:String,
       picture:String,
       standard:String,
       mobile:Long,
       bio:String,
        token: String
    ): MutableLiveData<Response<ResponseBody>> {
        val result = Api.createStudent(
          name, gender, birth, picture, standard, mobile, bio, "Bearer ${token}")
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if(response.isSuccessful)
                {
                    createStudentLiveData.postValue(Response.Success())
                }
                else
                {
                    createStudentLiveData.postValue(Response.Error("Something went wrong . Please try again !!"))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                createStudentLiveData.postValue(Response.Error("Something went wrong . Please try again!!"))
            }
        })
        return createStudentLiveData
    }
}