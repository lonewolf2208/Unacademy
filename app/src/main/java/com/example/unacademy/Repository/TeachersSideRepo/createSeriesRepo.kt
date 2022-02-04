package com.example.unacademy.Repository.TeachersSideRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class createSeriesRepo(private val Api:Api) {
    private val createSeriesLiveData = MutableLiveData<Response<ResponseBody>>()

    val createSeriesResponse: LiveData<Response<ResponseBody>>
        get() = createSeriesLiveData

    fun createSeriesApi(name:String,description:String,icon:String,token:String)
    {
       Api.createSeries(name, icon, description, "Bearer ${token}").enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if(response.isSuccessful)
                {
                    createSeriesLiveData.postValue(Response.Success())
                }
                else
                {
                    getNewToken(Api).getToken()
                    createSeriesApi(name,description,icon,getNewToken.acessTOken.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
               createSeriesLiveData.postValue(Response.Error(t.message.toString()))
            }
        })
    }
}