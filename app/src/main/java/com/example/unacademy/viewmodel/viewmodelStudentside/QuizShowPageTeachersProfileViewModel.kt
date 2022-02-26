package com.example.unacademy.viewmodel.viewmodelStudentside

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.TeacherProfileRepoStudentSide
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.StudentSideModel.teachersProfileModel.teacher_profile_student_side
import kotlinx.coroutines.launch

class QuizShowPageTeachersProfileViewModel: ViewModel()
{
    var token :String=""

    suspend fun getProfile(id:Int): MutableLiveData<Response<teacher_profile_student_side>> {
        val Api= RetrofitClient.init()
        val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        Log.w("asdqwqeqvv",id.toString())
        var getTeacherProfileRepo= TeacherProfileRepoStudentSide(Api)
        var result=getTeacherProfileRepo.teacherProfileApi(id,token.toString())

        return result
    }
}