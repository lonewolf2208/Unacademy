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
        if(dateofbirth.value.isNullOrEmpty())
        {
            helpertextdon.value="Enter DOB"
        }
        else
        {
            return null
        }
        return Unit
    }
}