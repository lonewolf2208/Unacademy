package com.example.unacademy.viewmodel.viewmodelStudentside

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.QuizResultRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.Ui.StudentsSide.homePageStudentSide
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.QuizResultModelItem
import kotlinx.coroutines.launch

class GetQuizResultViewModel:ViewModel() {
    var noofQuestions = MutableLiveData<String>()
    var attemptedQuestions=MutableLiveData<String>()
    var correctedQuestions=MutableLiveData<String>()
    var wrongQuestions=MutableLiveData<String>()
    var token:String=""
    suspend fun QuizResult(): MutableLiveData<Response<List<QuizResultModelItem>>> {
        var Api=RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var QuizResultRepo=QuizResultRepo(Api)
        var result=QuizResultRepo.QuizResultApi(homePageStudentSide.quizid.toInt(),token.toString())
        Log.d("QuizIdToken",homePageStudentSide.quizid.toString())
        return result
    }

}