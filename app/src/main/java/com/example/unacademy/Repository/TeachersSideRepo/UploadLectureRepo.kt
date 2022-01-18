package com.example.unacademy.Repository.TeachersSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class UploadLectureRepo(val Api:Api) {
    private val uploadLectureLiveData = MutableLiveData<Response<ResponseBody>>()
    fun uploadLecturesApi(
        name:String,
        description:String,
        movie:String,
        token: String,
        id:Int
    ): MutableLiveData<Response<ResponseBody>> {

        val result = Api.UploadLectures(
            id,
            name,
            description,
            movie,
            "Bearer ${token}"
        )
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    uploadLectureLiveData.postValue(Response.Success())

                } else {
                    uploadLectureLiveData.postValue(Response.Error(response.message().toString()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                uploadLectureLiveData.postValue(Response.Error(t.message.toString()))
            }
        })
        return uploadLectureLiveData
    }
}