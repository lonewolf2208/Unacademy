package com.example.unacademy.Repository.TeachersSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.Ui.TeachersSide.CreateAQuizTeacherSide
import com.example.unacademy.api.Api
import com.example.unacademy.models.TeachersSideModels.CreateQuizModel.CreateQuizModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class UploadQuizQuestionRepo(var Api:Api) {
    private val uploadQuizQuestionLiveData = MutableLiveData<Response<ResponseBody>>()
    fun uploadQuizQuestionApi(
        question:String,
        marks:Int,
        option1:String,
        option2:String,
        option3:String,
        option4:String,
        answer:Int,
        token: String
    ): MutableLiveData<Response<ResponseBody>> {

        val result = Api.UploadQuizQuestion(CreateAQuizTeacherSide.Quizid.toInt(),question,marks,option1,option2,option3,option4,answer,"Bearer ${token}")
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    uploadQuizQuestionLiveData.postValue(Response.Success())

                } else {
                    getNewToken(Api).getToken()
                    uploadQuizQuestionApi(question, marks, option1, option2, option3, option4, answer,getNewToken.acessTOken.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                uploadQuizQuestionLiveData.postValue(Response.Error(t.message.toString()))
            }
        })
        return uploadQuizQuestionLiveData
    }
}