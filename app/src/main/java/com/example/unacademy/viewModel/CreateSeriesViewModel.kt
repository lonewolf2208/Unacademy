package com.example.unacademy.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.TeachersSideRepo.createSeriesRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import okhttp3.internal.wait

class CreateSeriesViewModel:ViewModel() {
    var courseName=MutableLiveData<String>()
    var coursedescription=MutableLiveData<String>()
    var icon=MutableLiveData<String>()
    var token:String?=null
    suspend fun createSeries(): LiveData<Response<ResponseBody>> {
        var Api=RetrofitClient.init()
         val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        val createSeriesRepo=createSeriesRepo(Api)
        createSeriesRepo.createSeriesApi(courseName.value.toString(),coursedescription.value.toString(),icon.value.toString(),token.toString())
        var result=createSeriesRepo.createSeriesResponse
        return result

    }
}