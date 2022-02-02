package com.example.unacademy.Repository.StudentSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentStory.studentStoryModelItem
import retrofit2.Call
import retrofit2.Callback

class StudentStoryProfileRepo(val Api:Api) {
    companion object {
        var studentStoryDataName = java.util.ArrayList<String>()
        var studetStoryDataPicture=java.util.ArrayList<String>()
        var studentStoryId=java.util.ArrayList<Int>()
    }

    private val getStudentStoryProfileLiveData =
        MutableLiveData<Response<ArrayList<studentStoryModelItem>>>()

    fun getStudentStoryApi(
        token: String
    ): MutableLiveData<Response<ArrayList<studentStoryModelItem>>> {
        val result = Api.studentStoryProfile("Bearer ${token}")
        result.enqueue(object : Callback<ArrayList<studentStoryModelItem>?> {
            override fun onResponse(
                call: Call<ArrayList<studentStoryModelItem>?>,
                response: retrofit2.Response<ArrayList<studentStoryModelItem>?>
            ) {
                when
                {
                    response.isSuccessful->
                    {
                        for(i in 0..(response.body()!!.size-1))
                        {
                            var flag=0
                            if(response.body()!![i].name in StudentStoryProfileRepo.studentStoryDataName)
                            {
                                continue
                            }
                            else
                            {
                                StudentStoryProfileRepo.studentStoryDataName.add(response.body()!![i].name)
                                StudentStoryProfileRepo.studetStoryDataPicture.add(response.body()!![i].picture)
                                StudentStoryProfileRepo.studentStoryId.add(response.body()!![i].educator)
                            }
                            Log.d("Sisdas", StudentStoryProfileRepo.studentStoryDataName.size.toString())
                        }
                        getStudentStoryProfileLiveData.postValue(Response.Success(response.body()))
                    }
                    else->
                    {
                        getNewToken(Api).getToken()
                       getStudentStoryApi( getNewToken.acessTOken.toString())
                    }
                }


            }
            override fun onFailure(call: Call<ArrayList<studentStoryModelItem>?>, t: Throwable) {
                getStudentStoryProfileLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return getStudentStoryProfileLiveData
    }
}



//
//for(i in 0..response.body()!!.size)
//{
//    var flag=0
//    if(response.body()!![i].name in StudentStoryProfileRepo.studentStoryData)
//    {
//
//        flag=1
//        break
//    }
//    if(flag==0)
//    {
//        StudentStoryProfileRepo.studentStoryData.add(response.body()!![i].name)
//    }
//}
