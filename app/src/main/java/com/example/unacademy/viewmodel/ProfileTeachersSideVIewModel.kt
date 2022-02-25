package com.example.unacademy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.TeachersSideRepo.GetTeachersProfileTeachersSide
import com.example.unacademy.Repository.TeachersSideRepo.ProfileTeachersSideRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.TeachersSideModels.getTeachersProfile.getTeachersProfileModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class ProfileTeachersSideVIewModel:ViewModel() {
    var doc=MutableLiveData<String>()
    var token:String=""
    var result=MutableLiveData<Response<ResponseBody>>()
suspend fun UploadStory()
{
    var Api= RetrofitClient.init()
    val job =viewModelScope.launch {
        var AccessToken = Splash_Screen.readInfo("access").toString()
        token = AccessToken
    }
    job.join()
    var profileTeachersSideRepo=ProfileTeachersSideRepo(Api)
   result= profileTeachersSideRepo.uploadStory(doc.value.toString(),token.toString())
    Log.w("Upload Story",doc.value.toString())
}
    suspend fun GetProfile(): MutableLiveData<Response<getTeachersProfileModel>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var getTeachersProfileTeachersSide= GetTeachersProfileTeachersSide(Api)
        var result=getTeachersProfileTeachersSide.getTeachersPRofileApi(token.toString())
        return result
    }
}