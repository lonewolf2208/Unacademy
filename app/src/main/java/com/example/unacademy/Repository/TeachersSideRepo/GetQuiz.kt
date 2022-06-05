package com.example.unacademy.Repository.TeachersSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import retrofit2.Call
import retrofit2.Callback

class GetQuiz(val Api:Api) {
    private val getQUizLiveData = MutableLiveData<Response<List<StudentSideGetQuizModelItem>>>()
    fun getQUizApi(
        token: String
    ): MutableLiveData<Response<List<StudentSideGetQuizModelItem>>> {

        val result = Api.getQuiz(
            "Bearer ${token}"
        )
        result.enqueue(object : Callback<List<StudentSideGetQuizModelItem>?> {
            override fun onResponse(
                call: Call<List<StudentSideGetQuizModelItem>?>,
                response: retrofit2.Response<List<StudentSideGetQuizModelItem>?>
            ) {
                when
                {
                    response.isSuccessful->{getQUizLiveData.postValue(Response.Success(response.body()))}
                    else->{
                        getNewToken(Api).getToken()
                       getQUizApi(getNewToken.acessTOken.toString())
                    }
                }
            }

            override fun onFailure(
                call: Call<List<StudentSideGetQuizModelItem>?>,
                t: Throwable
            ) {
                getQUizLiveData.postValue(Response.Error("Something went wrong . Try again!!"))
            }
        })
        return getQUizLiveData
    }
}