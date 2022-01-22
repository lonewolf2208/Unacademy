package com.example.unacademy.viewmodel.viewmodelStudentside

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.CreateStudent
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class CreateStudentViewModel: ViewModel() {
    var picture= MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var mobileno= MutableLiveData<String>()
    var gender= MutableLiveData<String>()
    var dob= MutableLiveData<String>()
    var standard= MutableLiveData<String>()
    var bio= MutableLiveData<String>()
    var token :String=""
    suspend fun createStudent(): MutableLiveData<Response<ResponseBody>> {
        var Api= RetrofitClient.init()
        val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var createStudent= CreateStudent(Api)
        var result=createStudent.createStudentApi(name.value.toString(),gender.value.toString(),dob.value.toString(), picture.value.toString(),standard.value.toString(),
            mobileno.value!!.toLong(),bio.value.toString(),token)
        return result
    }
}