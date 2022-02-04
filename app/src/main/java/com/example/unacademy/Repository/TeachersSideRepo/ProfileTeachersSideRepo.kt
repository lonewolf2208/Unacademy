package com.example.unacademy.Repository.TeachersSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class ProfileTeachersSideRepo(private val Api:Api) {
    private val uploadStoryLiveData = MutableLiveData<Response<ResponseBody>>()
    fun uploadStory(doc:String,token:String): MutableLiveData<Response<ResponseBody>> {
        Api.UploadStory(doc,"Bearer ${token}").enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if(response.isSuccessful)
                {
                    uploadStoryLiveData.postValue(Response.Success())
                }
                else
                {
                    getNewToken(Api).getToken()
                    uploadStory(doc,getNewToken.acessTOken.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                uploadStoryLiveData.postValue(Response.Error(t.message.toString()))
            }
        })
        return uploadStoryLiveData
    }
}