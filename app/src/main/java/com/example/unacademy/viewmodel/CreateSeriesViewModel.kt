package com.example.unacademy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.TeachersSideRepo.createSeriesRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class CreateSeriesViewModel:ViewModel() {
    var helperTextName=MutableLiveData<String>()
    var helperTextDescription=MutableLiveData<String>()
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
    fun Validations():Unit?
    {
        if(courseName.value.isNullOrEmpty())
        {
            helperTextName.postValue("Add Series Name")
        }
        if(coursedescription.value.isNullOrEmpty())
        {
            helperTextDescription.postValue("Add Description")
        }
        if(icon.value.isNullOrEmpty())
        {

        }
        else
        {
            return null
        }
        return Unit
    }
}