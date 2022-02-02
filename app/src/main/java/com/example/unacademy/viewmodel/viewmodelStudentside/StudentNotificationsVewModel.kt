package com.example.unacademy.viewmodel.viewmodelStudentside

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.StudentNotificationsRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.StudentSideModel.StudentNotifications.StudentNotificationsModelItem
import kotlinx.coroutines.launch

class StudentNotificationsVewModel: ViewModel()
{
    var token:String=""
    suspend fun getStudentNotification(): MutableLiveData<Response<List<StudentNotificationsModelItem>>> {
        var Api=RetrofitClient.init()
        var studentNotificationsRepo=StudentNotificationsRepo(Api)
        var job=viewModelScope.launch {
            token=Splash_Screen.readInfo("access").toString()
        }
        job.join()
        var result=studentNotificationsRepo.GetStudentNotififcationsApi(token)
        return result
    }
}