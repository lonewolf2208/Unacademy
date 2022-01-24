package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.QuizQuestionsModel.quizQuestionsModel
import com.example.unacademy.models.StudentSideGetQuiz.StudentSideGetQuizModelItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class getQuizQuestionsRepo(var Api:Api) {
    private val getQuizQuestionsLiveData = MutableLiveData<Response<List<quizQuestionsModel>>>()
    fun getQuizStudentSideApi(
        id: Int,
        token: String
    ): MutableLiveData<Response<List<quizQuestionsModel>>> {
        val result = Api.getQuizQuestions(id, "Bearer ${token}")
       result.enqueue(object : Callback<List<quizQuestionsModel>?> {
           override fun onResponse(
               call: Call<List<quizQuestionsModel>?>,
               response: retrofit2.Response<List<quizQuestionsModel>?>
           ) {
               if(response.isSuccessful)
               {
                   getQuizQuestionsLiveData.postValue(Response.Success(response.body()))
               }
               else
               {
                   getQuizQuestionsLiveData.postValue(Response.Error(response.message().toString()))
               }
           }

           override fun onFailure(call: Call<List<quizQuestionsModel>?>, t: Throwable) {
               getQuizQuestionsLiveData.postValue(Response.Error(t.localizedMessage.toString()))
           }
       })
        return getQuizQuestionsLiveData
    }
}