package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.StudentNotifications.StudentNotificationsModelItem
import retrofit2.Call
import retrofit2.Callback

class StudentNotificationsRepo(val Api:Api)
{
    private val getStudentNotificationsLiveData = MutableLiveData<Response<List<StudentNotificationsModelItem>>>()
    fun GetStudentNotififcationsApi(
        token: String
    ): MutableLiveData<Response<List<StudentNotificationsModelItem>>> {
        val result = Api.getStudentNotifications("Bearer ${token}")
       result.enqueue(object : Callback<List<StudentNotificationsModelItem>?> {
           override fun onResponse(
               call: Call<List<StudentNotificationsModelItem>?>,
               response: retrofit2.Response<List<StudentNotificationsModelItem>?>
           ) {
               when
               {
                   response.isSuccessful->getStudentNotificationsLiveData.postValue(Response.Success(response.body()))
                   else->
                   {
                       getNewToken(Api).getToken()
                       GetStudentNotififcationsApi(getNewToken.acessTOken.toString())
                   }
               }
           }

           override fun onFailure(call: Call<List<StudentNotificationsModelItem>?>, t: Throwable) {
               getStudentNotificationsLiveData.postValue(Response.Error(t.message.toString()))
           }
       })
        return getStudentNotificationsLiveData
    }
}
