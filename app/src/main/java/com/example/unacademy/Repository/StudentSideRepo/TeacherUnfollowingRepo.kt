package com.example.unacademy.Repository.StudentSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class TeacherUnfollowingRepo(var Api:Api)
{
    private val teacherUnFollowingInfoLiveData = MutableLiveData<Response<ResponseBody>>()
    fun teacherUnFollowingApi(
        following: Int,
        token: String
    ): MutableLiveData<Response<ResponseBody>> {
        val result = Api.removeFollowing(following, "Bearer ${token}")
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                Log.w("NotFollowinggg",response.message().toString())
                if (response.isSuccessful) {
                    teacherUnFollowingInfoLiveData.postValue(Response.Success())

                } else {
                    teacherUnFollowingInfoLiveData.postValue(
                        Response.Error(
                            response.message().toString()
                        )
                    )
                }
            }


            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                teacherUnFollowingInfoLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return teacherUnFollowingInfoLiveData
    }
}