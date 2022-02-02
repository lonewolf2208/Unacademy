package com.example.unacademy.Repository.StudentSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.getStudentSeries.studentStories.StudentStoryInfoModelItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class TeacherFollowingRepo(var Api:Api) {
    private val teacherFollowingInfoLiveData = MutableLiveData<Response<ResponseBody>>()
    fun teacherFollowingApi(
        following: Int,
        token: String
    ): MutableLiveData<Response<ResponseBody>> {
        val result = Api.addFollowing(following, "Bearer ${token}")
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                Log.w("Followinggg",response.message().toString())
                when
                {
                    response.isSuccessful-> teacherFollowingInfoLiveData.postValue(Response.Success())
                    else->
                    {
                        getNewToken(Api).getToken()
                       teacherFollowingApi(following, getNewToken.acessTOken.toString())
                    }
                }
            }


            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                teacherFollowingInfoLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return teacherFollowingInfoLiveData
    }
}
