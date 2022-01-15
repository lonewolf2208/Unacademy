package com.example.unacademy.Repository.TeachersSideRepo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class TeachersProfileRepo(private val Api: Api) {
    private val teacherProfileLiveData = MutableLiveData<Response<ResponseBody>>()
    fun teachersProfileApi(
        teachersProfileDataClass: teachersProfileDataClass,
        token: String
    ): MutableLiveData<Response<ResponseBody>> {

        teacherProfileLiveData.postValue(Response.Loading())
        val result = Api.teachersProfile(
            teachersProfileDataClass.name.toString(),
            teachersProfileDataClass.mobile!!.toLong(),
            "Male",
            teachersProfileDataClass.birth.toString(),
            teachersProfileDataClass.picture.toString(),
            teachersProfileDataClass.qual.toString(),
            teachersProfileDataClass.bio.toString(),
            teachersProfileDataClass.sample_video.toString(),
            "Bearer ${token}"
        )
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    Log.w("sda", "Successsfull")
                    teacherProfileLiveData.postValue(Response.Success())
                } else {
                    teacherProfileLiveData.postValue(Response.Error("Some Error has occured please try again!!"))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.w("sda", "Failure")
                teacherProfileLiveData.postValue(Response.Error(t.message.toString()))
            }
        })
        return teacherProfileLiveData
    }
}