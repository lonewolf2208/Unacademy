package com.example.unacademy.viewModel

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
import retrofit2.Response

class UploadLectureViewModel:ViewModel() {
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
}