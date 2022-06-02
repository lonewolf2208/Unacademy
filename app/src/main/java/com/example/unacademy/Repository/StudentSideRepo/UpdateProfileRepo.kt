package com.example.unacademy.Repository.StudentSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class UpdateProfileRepo(var Api:Api) {
    private val updateStudentLiveData = MutableLiveData<Response<ResponseBody>>()
    fun updateStudentApi(
        name:String,
        gender:String,
        birth:String,
        picture:String,
        standard:String,
        mobile:Long,
        bio:String,
        token: String
    ): MutableLiveData<Response<ResponseBody>> {

        val result = Api.UpdateStudentProfile(
            name, gender, birth, picture, standard, mobile, bio, "Bearer ${token}")
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                Log.d("asdasdawqeqr",response.code().toString())
                if(response.isSuccessful)
                {
                    updateStudentLiveData.postValue(Response.Success())
                }
                else
                {
                    updateStudentLiveData.postValue(Response.Error("Something went wrong . Please try again !!"))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                updateStudentLiveData.postValue(Response.Error("Something went wrong . Please try again!!"))
            }
        })
        return updateStudentLiveData
    }
}