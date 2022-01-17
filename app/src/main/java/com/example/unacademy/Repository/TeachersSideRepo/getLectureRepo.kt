package com.example.unacademy.Repository.TeachersSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.TeachersSideModels.getLectureModelItem
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class getLectureRepo(val Api:Api) {
    private val getLectureLiveData = MutableLiveData<Response<List<getLectureModelItem>>>()
    fun getLectureApi(
        series: Int,
        token: String
    ): MutableLiveData<Response<List<getLectureModelItem>>> {
        val result = Api.getLectures(series, "Bearer ${token}")
    result.enqueue(object : Callback<List<getLectureModelItem>?> {
        override fun onResponse(
            call: Call<List<getLectureModelItem>?>,
            response: retrofit2.Response<List<getLectureModelItem>?>
        ) {
            if(response.isSuccessful)
            {
                getLectureLiveData.postValue(Response.Success(response.body()))
            }
            else
            {
                getLectureLiveData.postValue(Response.Error(response.message().toString()))
            }
        }

        override fun onFailure(call: Call<List<getLectureModelItem>?>, t: Throwable) {
            getLectureLiveData.postValue(Response.Error(t.message.toString()))
        }
    })
        return getLectureLiveData
    }
}