package com.example.unacademy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.TeachersSideRepo.GetQuiz
import com.example.unacademy.Repository.TeachersSideRepo.GetTeachersProfileTeachersSide
import com.example.unacademy.Repository.TeachersSideRepo.getSeriesRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import com.example.unacademy.models.TeachersSideModels.getTeachersProfile.getTeachersProfileModel
import kotlinx.coroutines.launch

class HomePageViewModel():ViewModel() {
    var token:String?=null
    var result=MutableLiveData<Response<List<getStudentSeriesItem>>>()
    suspend fun getSeries()
    {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var getSeriesRepo=getSeriesRepo(Api)
        result=getSeriesRepo.getSeriesApi(token.toString())
    }
    suspend fun GetProfile(): MutableLiveData<Response<getTeachersProfileModel>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var getTeachersProfileTeachersSide=GetTeachersProfileTeachersSide(Api)
        var result=getTeachersProfileTeachersSide.getTeachersPRofileApi(token.toString())
        return result
    }
    suspend fun getQuiz(): MutableLiveData<Response<List<StudentSideGetQuizModelItem>>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var getSeriesRepo=GetQuiz(Api)
        return getSeriesRepo.getQUizApi(token.toString())
    }
}