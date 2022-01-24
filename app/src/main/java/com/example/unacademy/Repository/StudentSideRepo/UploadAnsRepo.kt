package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideGetQuiz.StudentSideGetQuizModelItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class UploadAnsRepo(var Api:Api)
{
    private val uploadAnsLiveData = MutableLiveData<Response<ResponseBody>>()
    fun uploadAnsApi(
        id:Int,
        answer:Int,
        token: String
    ): MutableLiveData<Response<ResponseBody>> {
        val result = Api.UploadQuestionAns(id,answer,"Bearer ${token}")
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if(response.isSuccessful)
                {
                    uploadAnsLiveData.postValue(Response.Success())
                }
                else
                {
                    uploadAnsLiveData.postValue(Response.Error(response.message().toString()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
               uploadAnsLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return uploadAnsLiveData
    }
}