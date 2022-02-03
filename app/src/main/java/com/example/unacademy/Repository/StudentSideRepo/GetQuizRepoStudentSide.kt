package com.example.unacademy.Repository.StudentSideRepo

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback

class GetQuizRepoStudentSide(var Api:Api) {
    private val getQuizLiveData = MutableLiveData<Response<List<StudentSideGetQuizModelItem>>>()
    fun getQuizStudentSideApi(
        token: String
    ): MutableLiveData<Response<List<StudentSideGetQuizModelItem>>> {
        val result = Api.getQuizStudentSide("Bearer ${token}")
        result.enqueue(object : Callback<List<StudentSideGetQuizModelItem>?> {
            override fun onResponse(
                call: Call<List<StudentSideGetQuizModelItem>?>,
                response: retrofit2.Response<List<StudentSideGetQuizModelItem>?>
            ) {

                if (response.isSuccessful) {
                    getQuizLiveData.postValue(Response.Success(response.body()))
                }
                else
                {
                    getNewToken(Api).getToken()
                    getQuizStudentSideApi(getNewToken.acessTOken)
                }
            }

            override fun onFailure(call: Call<List<StudentSideGetQuizModelItem>?>, t: Throwable) {
                getQuizLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return getQuizLiveData
    }
}