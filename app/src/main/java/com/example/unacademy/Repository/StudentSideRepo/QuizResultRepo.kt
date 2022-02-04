package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.QuizResultModelItem
import retrofit2.Call
import retrofit2.Callback

class QuizResultRepo(var Api:Api) {
    private val QuizResultRepoLiveData = MutableLiveData<Response<List<QuizResultModelItem>>>()
    fun QuizResultApi(
        id: Int,
        token: String
    ): MutableLiveData<Response<List<QuizResultModelItem>>> {
        val result = Api.getQuizResult(id.toInt(), "Bearer ${token}")
        result.enqueue(object : Callback<List<QuizResultModelItem>?> {
            override fun onResponse(
                call: Call<List<QuizResultModelItem>?>,
                response: retrofit2.Response<List<QuizResultModelItem>?>
            ) {
                when
                {
                    response.isSuccessful-> QuizResultRepoLiveData.postValue(Response.Success(response.body()))
                    else->
                    {
                        getNewToken(Api).getToken()
                        QuizResultApi(id,getNewToken.acessTOken.toString())
                    }
                }

            }
            override fun onFailure(call: Call<List<QuizResultModelItem>?>, t: Throwable) {
                QuizResultRepoLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return QuizResultRepoLiveData
    }
}




