package com.example.unacademy.Repository.TeachersSideRepo

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class TeachersProfileRepo(private val Api:Api)
{
    private val TeachersProfileLiveData = MutableLiveData<Response<teachersProfileDataClass>>()

    val TeachersProfileResponse: LiveData<Response<teachersProfileDataClass>>
        get() = TeachersProfileLiveData

    fun TeachersProfileApi(teachersProfileDataClass: teachersProfileDataClass,token:String)
    {

        val result = Api.teachersProfile(teachersProfileDataClass,token)
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if(response.isSuccessful)
                {
                    TeachersProfileLiveData.postValue(Response.Success())
                }
                else
                {
                    TeachersProfileLiveData.postValue(Response.Error(response.code().toString()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.d("Errormy",t.toString())
                    TeachersProfileLiveData.postValue(Response.Error(t.localizedMessage))
            }
        })

    }
}