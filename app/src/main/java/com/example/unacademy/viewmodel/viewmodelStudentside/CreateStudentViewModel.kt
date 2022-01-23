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
    var helpertextstandard=MutableLiveData<String>()
    var helpertextdob=MutableLiveData<String>()
    var helperTextGender=MutableLiveData<String>()
    var helperTextImage=MutableLiveData<String>()
    var helperTextmobile=MutableLiveData<String>()
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
    fun dobValidations():Unit?
    {
        var dob = dob.value.toString()
        if(dob.isNullOrEmpty())
        {
            helpertextdob.postValue("Invalid Format")
            return Unit
        }

        if(dob.length<10)
        {
            helpertextdob.postValue("Invalid Format")
            return Unit
        }
        for(i in 0..3)
        {
            var value = dob[i].toInt()
            if(!(value in 0..9))
            {
                helpertextdob.postValue("Invalid Format")
            }
        }
        for (j in 5..6)
        {
            var value = dob[j].toInt()
            if(!(value in 0..9))
            {
                helpertextdob.postValue("Invalid Format")
            }

        }
        for (j in 8..9)
        {
            var value = dob[j].toInt()
            if(!(value in 0..9))
            {
                helpertextdob.postValue("Invalid Format")
            }

        }
        if((dob[4] != '-')  && (dob[7] !='-'))
        {
            helpertextdob.postValue("Invalid Format")
        }
        return null
    }
    fun validations(): Unit?{
        if(mobileno.value.isNullOrEmpty())
        {
            helperTextmobile.postValue("Please Enter Your Mobile no.")
        }
        if(picture.value.isNullOrEmpty())
        {
            helperTextImage.postValue("Please Update Your Profile Picture")
        }
        if(standard.value.isNullOrEmpty())
        {
            helpertextstandard.postValue("Please Enter Your Class")
        }
        if(gender.value.isNullOrEmpty() || gender.value.toString().trim()=="Select Your Gender")
        {
            helperTextGender.postValue("Please Select Your Gender")
        }
        if(dobValidations()!=null)
        {
            return Unit
        }
        else
        {
            return null
        }
        return Unit
    }
}