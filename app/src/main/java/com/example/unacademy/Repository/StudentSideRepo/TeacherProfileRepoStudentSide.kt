package com.example.unacademy.Repository.StudentSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.teachersProfileModel.teacher_profile_student_side
import retrofit2.Call
import retrofit2.Callback

class TeacherProfileRepoStudentSide(var Api:Api)
{
    private val teacherFollowingInfoLiveData = MutableLiveData<Response<teacher_profile_student_side>>()
    fun teacherProfileApi(
        teacherid: Int,
        token: String
    ): MutableLiveData<Response<teacher_profile_student_side>> {
        val result = Api.getTeachersProfileStudentSide(teacherid, "Bearer ${token}")
       result.enqueue(object : Callback<teacher_profile_student_side?> {
           override fun onResponse(
               call: Call<teacher_profile_student_side?>,
               response: retrofit2.Response<teacher_profile_student_side?>
           ) {
               Log.d("asdqqgqvz",response.body().toString())
               when
               {
                   response.isSuccessful-> teacherFollowingInfoLiveData.postValue(Response.Success(response.body()))
                   else->
                   {

                       getNewToken(Api).getToken()
                       teacherProfileApi(teacherid, getNewToken.acessTOken)
                   }
               }
           }

           override fun onFailure(call: Call<teacher_profile_student_side?>, t: Throwable) {
              teacherFollowingInfoLiveData.postValue(Response.Error("Something went wrong!!"))
           }
       })
        return teacherFollowingInfoLiveData    }
}

