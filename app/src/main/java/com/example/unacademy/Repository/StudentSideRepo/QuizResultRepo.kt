package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.QuizResultModelItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class QuizResultRepo(var Api:Api) {
    private val QuizResultRepoLiveData = MutableLiveData<Response<List<QuizResultModelItem>>>()
    fun QuizResultApi(
        id: Int,
        token: String
    ): MutableLiveData<Response<List<QuizResultModelItem>>> {
        val result = Api.getQuizResult(id.toInt(), "{Bearer ${token}}")
        result.enqueue(object : Callback<List<QuizResultModelItem>?> {
            override fun onResponse(
                call: Call<List<QuizResultModelItem>?>,
                response: retrofit2.Response<List<QuizResultModelItem>?>
            ) {
                if (response.isSuccessful) {
                    QuizResultRepoLiveData.postValue(Response.Success(response.body()))
                } else {
                    QuizResultRepoLiveData.postValue(Response.Error(response.code().toString()))
                }
            }

            override fun onFailure(call: Call<List<QuizResultModelItem>?>, t: Throwable) {
                QuizResultRepoLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return QuizResultRepoLiveData
    }
}



