package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import retrofit2.Call
import retrofit2.Callback

class getWislistedSeriesRepo(var Api:Api) {
    private val getWislistedSeriesRepoLiveData = MutableLiveData<Response<List<getStudentSeriesItem>>>()
    fun studentWishlistedSeriesApi(
        token: String
    ): MutableLiveData<Response<List<getStudentSeriesItem>>> {
        val result = Api.getWishlistedSeries("Bearer ${token}")
        result.enqueue(object : Callback<List<getStudentSeriesItem>?> {
            override fun onResponse(
                call: Call<List<getStudentSeriesItem>?>,
                response: retrofit2.Response<List<getStudentSeriesItem>?>
            ) {
                if (response.isSuccessful) {
                    getWislistedSeriesRepoLiveData.postValue(Response.Success(response.body()))
                } else {
                    getWislistedSeriesRepoLiveData.postValue(
                        Response.Error(
                            response.message().toString()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<List<getStudentSeriesItem>?>, t: Throwable) {
                getWislistedSeriesRepoLiveData.postValue(Response.Error(t.message.toString()))
            }
        })
        return getWislistedSeriesRepoLiveData
    }
}