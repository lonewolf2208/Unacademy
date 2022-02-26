package com.example.unacademy.viewmodel.viewmodelStudentside

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterOurEducatorsStudentSide
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.GetStudentProfileRepo
import com.example.unacademy.Repository.StudentSideRepo.TeacherFollowingRepo
import com.example.unacademy.Repository.StudentSideRepo.TeacherProfileRepoStudentSide
import com.example.unacademy.Repository.StudentSideRepo.TeacherUnfollowingRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.StudentSideModel.getStudentProfileModel.getStudentProfileModel
import com.example.unacademy.models.StudentSideModel.teachersProfileModel.teacher_profile_student_side
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class TeacherProfileShowStudentSideViewModel: ViewModel()
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
        var getTeacherProfileRepo=TeacherProfileRepoStudentSide(Api)
        var result=getTeacherProfileRepo.teacherProfileApi(id,token.toString())

        return result
    }
    suspend fun addFollowing(): MutableLiveData<Response<ResponseBody>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var TeacherFollowingRepo= TeacherFollowingRepo(Api)
        var result=TeacherFollowingRepo.teacherFollowingApi(RecyclerAdapterOurEducatorsStudentSide.educatorId.toInt(),token.toString())
        return result
    }
    suspend fun teacherUnfollowing(): MutableLiveData<Response<ResponseBody>> {
        var Api= RetrofitClient.init()
        val job= viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var TeacherFollowingRepo= TeacherUnfollowingRepo(Api)
        var result=TeacherFollowingRepo.teacherUnFollowingApi(RecyclerAdapterOurEducatorsStudentSide.educatorId,token.toString())
        return result
    }
}