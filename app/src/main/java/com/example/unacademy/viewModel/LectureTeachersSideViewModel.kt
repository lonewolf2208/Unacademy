package com.example.unacademy.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.TeachersSideRepo.getLectureRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.TeachersSideModels.getLectureModelItem
import kotlinx.coroutines.launch

class LectureTeachersSideViewModel(): ViewModel()
{
    var seriesName=MutableLiveData<String>()
    var seriesDescription=MutableLiveData<String>()
    var result=MutableLiveData<Response<List<getLectureModelItem>>>()
    var token:String?=null

    suspend fun getLectures(series:Int)
    {
        var Api=RetrofitClient.init()
        val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        val getLectureRepo=getLectureRepo(Api)
        result=getLectureRepo.getLectureApi(series,token.toString())
    }
}