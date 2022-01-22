package com.example.unacademy.viewmodel.viewmodelStudentside

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.OurEducatorRepo
import com.example.unacademy.Repository.StudentSideRepo.StudentStoryProfileRepo
import com.example.unacademy.Repository.StudentSideRepo.getStudentSeriesRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.StudentSideModel.getStudentSeries.EducatorDetails
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.models.StudentStory.studentStoryModelItem
import kotlinx.coroutines.launch

class HomePageStudentSideViewModel:ViewModel()
{
    var token:String?=null
    var result= MutableLiveData<Response<List<getStudentSeriesItem>>>()
    var ourEducatorsResult=MutableLiveData<Response<List<EducatorDetails>>>()
    var studentStoryProfileResult=MutableLiveData<Response<List<studentStoryModelItem>>>()
    suspend fun getSeries(): MutableLiveData<Response<List<getStudentSeriesItem>>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var getStudentSeriesRepo=getStudentSeriesRepo(Api)
        result=getStudentSeriesRepo.studentSeriesApi(token.toString())
        return result
    }
    suspend fun getEducators(): MutableLiveData<Response<List<EducatorDetails>>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var getEducatorRepo=OurEducatorRepo(Api)
        ourEducatorsResult=getEducatorRepo.ourEducatorApi(token.toString())
        return ourEducatorsResult
    }
    suspend fun getStudentStoryProfile(): MutableLiveData<Response<List<studentStoryModelItem>>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var studentStoryProfileRepo=StudentStoryProfileRepo(Api)
        studentStoryProfileResult=studentStoryProfileRepo.getStudentStoryApi(token.toString())
        return studentStoryProfileResult
    }
}