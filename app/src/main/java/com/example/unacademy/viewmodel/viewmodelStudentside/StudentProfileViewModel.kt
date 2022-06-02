package com.example.unacademy.viewmodel.viewmodelStudentside

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.GetStudentProfileRepo
import com.example.unacademy.Repository.StudentSideRepo.StudentStoryInfoRepo
import com.example.unacademy.Repository.StudentSideRepo.UpdateProfileRepo
import com.example.unacademy.Repository.StudentSideRepo.getWislistedSeriesRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.StudentSideModel.getStudentProfileModel.getStudentProfileModel
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class StudentProfileViewModel:ViewModel() {
    var token :String=""
    suspend fun getWishlistSeries(): MutableLiveData<Response<ArrayList<getStudentSeriesItem>>> {
        val Api= RetrofitClient.init()

        val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var getWislistedSeriesRepo=getWislistedSeriesRepo(Api)
        var result=getWislistedSeriesRepo.studentWishlistedSeriesApi(token.toString())
        return result
    }
    suspend fun getProfile(): MutableLiveData<Response<getStudentProfileModel>> {
        val Api= RetrofitClient.init()
        val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var getStudentProfileRepo=GetStudentProfileRepo(Api)
        var result=getStudentProfileRepo.getStudentProfileApi(token.toString())
        return result
    }
    suspend fun UpdatePofile(name:String,gender:String,birth:String,picture:String,standard:String,mobile:Long,bio:String):MutableLiveData<Response<ResponseBody>>
    {
        val Api= RetrofitClient.init()
        val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var updateStudentProfileRepo=UpdateProfileRepo(Api)
        var result=updateStudentProfileRepo.updateStudentApi(name, gender, birth, picture, standard, mobile, bio, token.toString())
        return result
    }
}