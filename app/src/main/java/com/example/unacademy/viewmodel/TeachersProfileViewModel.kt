package com.example.unacademy.viewmodel


import androidx.lifecycle.*
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.TeachersSideRepo.TeachersProfileRepo
import com.example.unacademy.Ui.Auth.Splash_Screen

import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
import kotlinx.coroutines.launch


class TeachersProfileViewModel():ViewModel()
{
    var helpertextdon=MutableLiveData<String>()
    var helperTextmobile=MutableLiveData<String>()
    var helperTextImage=MutableLiveData<String>()
    var helperTextGender=MutableLiveData<String>()
    var imageUrl=MutableLiveData<String>()
    var VideoUrl=MutableLiveData<String>()
    var name=MutableLiveData<String>()
    var mobileno=MutableLiveData<String>()
    var gender=MutableLiveData<String>()
    var dateofbirth=MutableLiveData<String>()
    var educationdetails=MutableLiveData<String>()
    var experience = MutableLiveData<String>()
    var token:String?=null

    var result=MutableLiveData<Response<teachersProfileDataClass>>()

    suspend fun submitData()
    {
        var Api=RetrofitClient.init()
       val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var teachersProfileDataClass= teachersProfileDataClass(
            name.value.toString()
            ,mobileno.value?.toLong()
            ,gender.value.toString()
            ,dateofbirth.value.toString()
            ,imageUrl.value.toString()
            ,educationdetails.value.toString()
            ,experience.value.toString()
            ,VideoUrl.value.toString())
        val teachersProfileRepo: TeachersProfileRepo=TeachersProfileRepo(Api = Api)
        result=teachersProfileRepo.teachersProfileApi(teachersProfileDataClass,token = token.toString())

    }



    fun dobValidations():Unit?
    {
        var dob = dateofbirth.value.toString()
        if(dob.isNullOrEmpty())
        {
            helpertextdon.postValue("Invalid Format")
            return Unit
        }
        if(dob.length<10)
        {
            helpertextdon.postValue("Invalid Format")
            return Unit
        }
        for(i in 0..3)
        {
            var value = dob[i].toInt()
            if(!(value in 0..9))
            {
                helpertextdon.postValue("Invalid Format")
            }
        }
        for (j in 5..6)
        {
            var value = dob[j].toInt()
            if(!(value in 0..9))
            {
                helpertextdon.postValue("Invalid Format")
            }

        }
        for (j in 8..9)
        {
            var value = dob[j].toInt()
            if(!(value in 0..9))
            {
                helpertextdon.postValue("Invalid Format")
            }

        }
        if((dob[4] != '-')  && (dob[7] !='-'))
        {
            helpertextdon.postValue("Invalid Format")

        }
        return null
    }
    fun validations(): Unit?{
        if(mobileno.value.isNullOrEmpty())
        {
            helperTextmobile.postValue("Please Enter Your Mobile no.")
        }
        if(imageUrl.value.isNullOrEmpty())
        {
            helperTextImage.postValue("Please Update Your Profile Picture")

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