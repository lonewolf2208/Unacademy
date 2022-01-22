package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.studentStories.StudentStoryInfoModelItem
import retrofit2.Call
import retrofit2.Callback

class StudentStoryInfoRepo(val Api:Api) {
    private val getStudentStoryInfoLiveData = MutableLiveData<Response<List<StudentStoryInfoModelItem>>>()
    fun studentStoryInfoApi(
        id:Int,
        token: String
    ): MutableLiveData<Response<List<StudentStoryInfoModelItem>>> {
        val result = Api.getStudentStory(id,"Bearer ${token}")
       result.enqueue(object : Callback<List<StudentStoryInfoModelItem>?> {
           override fun onResponse(
               call: Call<List<StudentStoryInfoModelItem>?>,
               response: retrofit2.Response<List<StudentStoryInfoModelItem>?>
           ) {
               if(response.isSuccessful)
               {
                   getStudentStoryInfoLiveData.postValue(Response.Success(response.body()))
               }
               else
               {
                   getStudentStoryInfoLiveData.postValue(Response.Error(response.message().toString()))
               }
           }

           override fun onFailure(call: Call<List<StudentStoryInfoModelItem>?>, t: Throwable) {
               getStudentStoryInfoLiveData.postValue(Response.Error(t.localizedMessage.toString()))
           }
       })
        return getStudentStoryInfoLiveData
    }
}