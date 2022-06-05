package com.example.unacademy.Repository.TeachersSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.Question
import com.example.unacademy.models.TeachersSideModels.UploadPdf.uploadpdf
import com.example.unacademy.models.TeachersSideModels.UploadPdf.uploadpdfItem
import retrofit2.Call
import retrofit2.Callback

class GetQUestions(var Api:Api) {
    private val getQUestionsLiveData = MutableLiveData<Response<List<Question>>>()
    fun getQuestionsApi(
        quizid:Int,
        token: String
    ): MutableLiveData<Response<List<Question>>> {
        val result = Api.getQuestions(quizid,"Bearer ${token}")
        result.enqueue(object : Callback<List<Question>?> {
            override fun onResponse(
                call: Call<List<Question>?>,
                response: retrofit2.Response<List<Question>?>
            ) {
                when
                {
                    response.isSuccessful->{getQUestionsLiveData.postValue(Response.Success(response.body())) }
                    else->{
                        getNewToken(Api).getToken()
                        getQuestionsApi(quizid,getNewToken.acessTOken.toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<Question>?>, t: Throwable) {
                getQUestionsLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return getQUestionsLiveData
    }
}