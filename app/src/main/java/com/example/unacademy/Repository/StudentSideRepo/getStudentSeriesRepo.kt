package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class getStudentSeriesRepo(val Api:Api) {
    private val getStudentSeriesLiveData = MutableLiveData<Response<List<getStudentSeriesItem>>>()
    fun studentSeriesApi(
        token: String
    ): MutableLiveData<Response<List<getStudentSeriesItem>>> {
        val result = Api.studentSeries("Bearer ${token}")
        result.enqueue(object : Callback<List<getStudentSeriesItem>?> {
            override fun onResponse(
                call: Call<List<getStudentSeriesItem>?>,
                response: retrofit2.Response<List<getStudentSeriesItem>?>
            ) {
                if (response.isSuccessful) {
                    getStudentSeriesLiveData.postValue(Response.Success(response.body()))
                } else {
                    getStudentSeriesLiveData.postValue(
                        Response.Error(
                            response.message().toString()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<List<getStudentSeriesItem>?>, t: Throwable) {
                getStudentSeriesLiveData.postValue(Response.Error(t.message.toString()))
            }
        })
        return getStudentSeriesLiveData
    }
}