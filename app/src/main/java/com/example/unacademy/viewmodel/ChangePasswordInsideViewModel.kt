package com.example.unacademy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.TeachersSideRepo.ChangePasswordInsideRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import kotlinx.coroutines.launch

class ChangePasswordInsideViewModel:ViewModel()
{
    var old_password=MutableLiveData<String>()
    var old_password_helperText=MutableLiveData<String>()
    var new_password_helper_text=MutableLiveData<String>()
    var new_password_confirm_helper_text=MutableLiveData<String>()
    var new_password=MutableLiveData<String>()
    var helperTextNewPasswordConfirm=MutableLiveData<String>()
    var new_password_confirm=MutableLiveData<String>()
    var token :String=""
    suspend fun changePassword(): MutableLiveData<Response<String>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var changePasswordInsideRepo=ChangePasswordInsideRepo(Api).ChangePasswordInsideApi(old_password.value.toString(),new_password_confirm.value.toString(),token.toString())
        return changePasswordInsideRepo
    }
    fun Validations():Unit?
    {
        if(new_password.value!=new_password_confirm.value)
        {
            helperTextNewPasswordConfirm.value="Password doesn't matches "
        }
        if(old_password.value.isNullOrEmpty())
        {
            old_password_helperText.value="Enter Old Password to continue"
        }
        if(new_password_confirm.value.isNullOrEmpty())
        {
            new_password_confirm_helper_text.value="Enter password to continue"
        }
        if(new_password.value.isNullOrEmpty())
        {
            new_password_helper_text.value="Enter password to continue"
        }
        else
        {
            return Unit
        }
        return null
    }
}