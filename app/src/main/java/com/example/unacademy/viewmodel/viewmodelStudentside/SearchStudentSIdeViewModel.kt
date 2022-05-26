package com.example.unacademy.viewmodel.viewmodelStudentside

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.GetQuizRepoStudentSide
import com.example.unacademy.Repository.StudentSideRepo.GetStudentProfileRepo
import com.example.unacademy.Repository.StudentSideRepo.SearchCourseRepo
import com.example.unacademy.Repository.StudentSideRepo.SearchProfileStudentSide
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.StudentSideModel.SearchStudentSide.SearchStudentSideItem
import com.example.unacademy.models.StudentSideModel.getSearchCourseModel.SearchCourseModelItem
import com.example.unacademy.models.StudentSideModel.getStudentProfileModel.getStudentProfileModel
import kotlinx.coroutines.launch

class SearchStudentSIdeViewModel: ViewModel() {
    var token:String=""
    suspend fun getProfile(username:String): MutableLiveData<Response<List<SearchStudentSideItem>>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var  getStudentProfileRepo=SearchProfileStudentSide(Api)
        var getProfileREsult=getStudentProfileRepo.searchEducatorProfileApi(token,username)
        return getProfileREsult
    }
    suspend fun getCourse(name:String): MutableLiveData<Response<List<SearchCourseModelItem>>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var  getCourseRepo=SearchCourseRepo(Api)
        var getCourseREsult=getCourseRepo.searchEducatorProfileApi(token,name)
        return getCourseREsult
    }

}