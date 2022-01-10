package com.example.unacademy.viewModel


import androidx.lifecycle.*
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.TeachersSideRepo.TeachersProfileRepo
import com.example.unacademy.Ui.Auth.Splash_Screen

import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class TeachersProfileViewModel():ViewModel()
{
    var helpertextdon=MutableLiveData<String>()
    var helperTextmobile=MutableLiveData<String>()
    var helperTextImage=MutableLiveData<String>()
    var imageUrl=MutableLiveData<String>()
    var VideoUrl=MutableLiveData<String>()
    var name=MutableLiveData<String>()
    var mobileno=MutableLiveData<String>()
    var gender=MutableLiveData<String>()
    var dateofbirth=MutableLiveData<String>()
    var educationdetails=MutableLiveData<String>()
    var experience = MutableLiveData<String>()
    var token:String?=null

    suspend fun submitData(): LiveData<Response<teachersProfileDataClass>>
    {
        var Api=RetrofitClient.init()
        val teachersProfileRepo: TeachersProfileRepo=TeachersProfileRepo(Api)
       val job= viewModelScope.async {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }

        var teachersProfileDataClass= teachersProfileDataClass(
            name.value.toString(),
            mobileno.value!!.toDouble()
            ,gender.value.toString()
            ,dateofbirth.value.toString()
            ,imageUrl.value
            ,educationdetails.value.toString()
            ,experience.value.toString()
            ,VideoUrl.value)

        teachersProfileRepo.TeachersProfileApi(teachersProfileDataClass,token = "${job.await()}")
        var result = teachersProfileRepo.TeachersProfileResponse
        return result
    }

    fun validations()
    {
        if(mobileno.value.isNullOrEmpty())
        {
            helperTextmobile.postValue("Please Enter Your Mobile no.")
        }
        if(imageUrl.value.isNullOrEmpty())
        {
            helperTextImage.postValue("Please Update Your Profile Picture")
        }

    }
}