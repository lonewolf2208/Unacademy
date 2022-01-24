package com.example.unacademy.viewmodel.viewmodelStudentside

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.*
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.Ui.StudentsSide.homePageStudentSide
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.EducatorDetails
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.models.StudentStory.studentStoryModelItem
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

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
    suspend fun addFollowing(): MutableLiveData<Response<ResponseBody>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var TeacherFollowingRepo=TeacherFollowingRepo(Api)
        var result=TeacherFollowingRepo.teacherFollowingApi(homePageStudentSide.educatorId.toInt(),token.toString())
        return result
    }
    suspend fun getQuiz(): MutableLiveData<Response<List<StudentSideGetQuizModelItem>>> {
        var Api=RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var  getQuizRepoStudentSide=GetQuizRepoStudentSide(Api)
        var result=getQuizRepoStudentSide.getQuizStudentSideApi(token.toString())
        return result
    }
}