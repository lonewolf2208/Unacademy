package com.example.unacademy.Repository.TeachersSideRepo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.Api
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import okhttp3.Dispatcher
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class TeachersProfileRepo(private val Api: Api) {
    private val teacherProfileLiveData = MutableLiveData<Response<teachersProfileDataClass>>()
    fun teachersProfileApi(
        teachersProfileDataClass: teachersProfileDataClass,
        token: String
    ): MutableLiveData<Response<teachersProfileDataClass>> {
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
        result.enqueue(object : Callback<teachersProfileDataClass?> {
            override fun onResponse(
                call: Call<teachersProfileDataClass?>,
                response: retrofit2.Response<teachersProfileDataClass?>
            ) {
                if (response.isSuccessful) {
                    Log.w("sda", "Successsfull")
                    teacherProfileLiveData.postValue(Response.Success(response.body()))
                }
//                else if(response.code()==400)
//                {
//                    getNewToken(Api).getToken()
//                }
                else {
                    teacherProfileLiveData.postValue(Response.Error("Some Error has occured please try again!!"))
                }
            }

            override fun onFailure(call: Call<teachersProfileDataClass?>, t: Throwable) {
                Log.w("sda", "Failure")
                teacherProfileLiveData.postValue(Response.Error(t.message.toString()))
            }
        })
        return teacherProfileLiveData
    }
}