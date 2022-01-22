package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentStory.studentStoryModelItem
import retrofit2.Call
import retrofit2.Callback

class StudentStoryProfileRepo(val Api:Api)
{
    companion object
    {
        var studentStoryData=ArrayList<String>()
    }
    private val getStudentStoryProfileLiveData = MutableLiveData<Response<List<studentStoryModelItem>>>()
    fun getStudentStoryApi(
        token: String
    ): MutableLiveData<Response<List<studentStoryModelItem>>> {
        val result = Api.studentStoryProfile("Bearer ${token}")
        result.enqueue(object : Callback<List<studentStoryModelItem>?> {
            override fun onResponse(
                call: Call<List<studentStoryModelItem>?>,
                response: retrofit2.Response<List<studentStoryModelItem>?>
            ) {

                if (response.isSuccessful)
                {
                    for(i in 0..response.body()!!.size)
                    {
                        var flag=0
                        if(response.body()!![i].name in studentStoryData)
                        {
                              flag=1
                            break
                        }
                        if(flag==0)
                        {
                            studentStoryData.add(response.body()!![i].name)
                        }
                    }
                    getStudentStoryProfileLiveData.postValue(Response.Success(response.body()))
                }
                else
                {
                    getStudentStoryProfileLiveData.postValue(Response.Error(response.message().toString()))
                }
            }

            override fun onFailure(call: Call<List<studentStoryModelItem>?>, t: Throwable) {
                getStudentStoryProfileLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })

        return getStudentStoryProfileLiveData
    }
}