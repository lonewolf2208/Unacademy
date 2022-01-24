package com.example.unacademy.viewmodel.viewmodelStudentside

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.UploadAnsRepo
import com.example.unacademy.Repository.StudentSideRepo.getQuizQuestionsRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.Ui.StudentsSide.homePageStudentSide
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.QuizQuestionsModel.quizQuestionsModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class GetQuizQuestionsViewModel:ViewModel()
{
    var token :String=""
    suspend fun getQuestions(): MutableLiveData<Response<List<quizQuestionsModel>>> {
        var Api=RetrofitClient.init()
        var getQuestionRepo=getQuizQuestionsRepo(Api)
        val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var result=getQuestionRepo.getQuizStudentSideApi(homePageStudentSide.quizid,token.toString())
        return result
    }
    suspend fun UploadQuestion(id:Int,answer:Int): MutableLiveData<Response<ResponseBody>> {
        var Api=RetrofitClient.init()
        var getQuestionRepo=getQuizQuestionsRepo(Api)
        val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var uploadAnsRepo=UploadAnsRepo(Api)
        var result=uploadAnsRepo.uploadAnsApi(id,answer,token)
        return result
    }
}