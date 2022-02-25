package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.getStudentProfileModel.getStudentProfileModel
import retrofit2.Call
import retrofit2.Callback

class GetStudentProfileRepo(val Api:Api)
{
    private val getStudentProfileLiveData = MutableLiveData<Response<getStudentProfileModel>>()
    fun getStudentProfileApi(
        token: String
    ): MutableLiveData<Response<getStudentProfileModel>> {
        val result = Api.GetStudentProfile("Bearer ${token}")
        result.enqueue(object : Callback<getStudentProfileModel?> {
            override fun onResponse(
                call: Call<getStudentProfileModel?>,
                response: retrofit2.Response<getStudentProfileModel?>
            ) {
                if (response.isSuccessful) {
                    getStudentProfileLiveData.postValue(Response.Success(response.body()))
                }
                else
                {
                    getNewToken(Api).getToken()
                    getStudentProfileApi(getNewToken.acessTOken)
                }
            }

            override fun onFailure(call: Call<getStudentProfileModel?>, t: Throwable) {
                getStudentProfileLiveData.postValue(Response.Error("Error"))
            }
        })
        return getStudentProfileLiveData
    }
}



