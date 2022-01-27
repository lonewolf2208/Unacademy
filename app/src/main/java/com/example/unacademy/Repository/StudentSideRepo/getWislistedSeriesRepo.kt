package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import retrofit2.Call
import retrofit2.Callback

class getWislistedSeriesRepo(var Api:Api) {
    private val getWislistedSeriesRepoLiveData = MutableLiveData<Response<ArrayList<getStudentSeriesItem>>>()
    fun studentWishlistedSeriesApi(
        token: String
    ): MutableLiveData<Response<ArrayList<getStudentSeriesItem>>> {
        val result = Api.getWishlistedSeries("Bearer ${token}")
        result.enqueue(object : Callback<ArrayList<getStudentSeriesItem>?> {
            override fun onResponse(
                call: Call<ArrayList<getStudentSeriesItem>?>,
                response: retrofit2.Response<ArrayList<getStudentSeriesItem>?>
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

            override fun onFailure(call: Call<ArrayList<getStudentSeriesItem>?>, t: Throwable) {
                getWislistedSeriesRepoLiveData.postValue(Response.Error(t.message.toString()))
            }
        })
        return getWislistedSeriesRepoLiveData
    }
}