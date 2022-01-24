package com.example.unacademy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.TeachersSideRepo.UploadLectureRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.api.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class UploadLectureViewModel:ViewModel() {
    var helperTextVideo=MutableLiveData<String>()
    var helperTextTitle=MutableLiveData<String>()
    var helperTextDescription=MutableLiveData<String>()
    var lectureTitle=MutableLiveData<String>()
    var lectureDescription=MutableLiveData<String>()
    var movieUrl=MutableLiveData<String>()
    var token:String?=null
    suspend fun uploadLectures(): MutableLiveData<com.example.unacademy.Repository.Response<ResponseBody>> {
        var Api=RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var uploadLectureRepo=UploadLectureRepo(Api)
        var result=uploadLectureRepo.uploadLecturesApi(lectureTitle.value.toString(),lectureDescription.value.toString(),movieUrl.value.toString(),token.toString(),
            HomePageTeachersSide.seriesid!!.toInt())
        Log.w("series", HomePageTeachersSide.seriesid!!.toString())
        return result
    }
    fun Validations():Unit?
    {
        if(lectureTitle.value.isNullOrEmpty())
        {
            helperTextTitle.postValue("Enter title!!")

        }
        if(lectureDescription.value.isNullOrEmpty())
        {
            helperTextDescription.postValue("Enter Description")
        }
        else {
            return null
        }
        return Unit
    }
}