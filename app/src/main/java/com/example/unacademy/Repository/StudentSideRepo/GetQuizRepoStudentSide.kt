package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem
import retrofit2.Call
import retrofit2.Callback

class GetQuizRepoStudentSide(var Api:Api) {
    companion object
    {
        var studentQuizWithNoZeroQuestions=ArrayList<StudentSideGetQuizModelItem>()
    }
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
                    studentQuizWithNoZeroQuestions= ArrayList<StudentSideGetQuizModelItem>()
                    for(i in 0 until response.body()?.size!!.toInt())
                    {
                        if(response.body()!![i].questions!=0)
                        {
                            studentQuizWithNoZeroQuestions.add(response.body()!![i])
                        }
                    }
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