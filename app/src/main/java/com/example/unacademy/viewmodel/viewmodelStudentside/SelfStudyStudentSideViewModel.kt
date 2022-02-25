package com.example.unacademy.viewmodel.viewmodelStudentside

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.GetQuizRepoStudentSide
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem
import kotlinx.coroutines.launch

class SelfStudyStudentSideViewModel: ViewModel()
{
    var token:String=""
    suspend fun getQuiz(): MutableLiveData<Response<List<StudentSideGetQuizModelItem>>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var  getQuizRepoStudentSide= GetQuizRepoStudentSide(Api)
        var getQuizREsult=getQuizRepoStudentSide.getQuizStudentSideApi(token.toString())
        return getQuizREsult
    }
}