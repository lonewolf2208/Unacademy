package com.example.unacademy.viewmodel.viewmodelStudentside

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.StudentStoryInfoRepo
import com.example.unacademy.Repository.TeachersSideRepo.getStoryRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.Ui.StudentsSide.homePageStudentSide
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.StudentSideModel.getStudentSeries.studentStories.StudentStoryInfoModelItem
import kotlinx.coroutines.launch

class StoryInfoViewModel:ViewModel() {
    var token :String=""

    suspend fun StudentStoryInfo(): MutableLiveData<Response<List<StudentStoryInfoModelItem>>> {
        val Api= RetrofitClient.init()
        var studentStoryInfoRepo=StudentStoryInfoRepo(Api)
        val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var result = studentStoryInfoRepo.studentStoryInfoApi(homePageStudentSide.studentStoryId,token)

        Log.w("idSTudentStory",homePageStudentSide().id.toString())
        return result
    }
}