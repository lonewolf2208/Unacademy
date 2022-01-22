package com.example.unacademy.Repository.TeachersSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.TeachersSideModels.getLectureModelItem
import com.example.unacademy.models.TeachersSideModels.getStoryModel
import com.example.unacademy.models.TeachersSideModels.getStoryModelItem
import retrofit2.Call
import retrofit2.Callback

class getStoryRepo(val Api:Api) {
    private val getStoryLiveData = MutableLiveData<Response<List<getStoryModelItem>>>()
    fun getStoryApi(
        token: String
    ): MutableLiveData<Response<List<getStoryModelItem>>> {
        val result = Api.getStory( "Bearer ${token}")
       result.enqueue(object : Callback<List<getStoryModelItem>?> {
           override fun onResponse(
               call: Call<List<getStoryModelItem>?>,
               response: retrofit2.Response<List<getStoryModelItem>?>
           ) {
               if(response.isSuccessful) {
                   getStoryLiveData.postValue(Response.Success(response.body()))
               }
               else if (response.code()==204)
               {
                   getStoryLiveData.postValue(Response.noStory())
               }
               else
               {
                   getStoryLiveData.postValue(Response.Error(response.code().toString()))
               }

           }

           override fun onFailure(call: Call<List<getStoryModelItem>?>, t: Throwable) {
               getStoryLiveData.postValue(Response.Error(t.localizedMessage.toString()))           }
       })
        return getStoryLiveData
    }
}