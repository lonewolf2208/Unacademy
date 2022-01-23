package com.example.unacademy.Repository.TeachersSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.TeachersSideModels.CreateQuizModel.CreateQuizModel
import com.example.unacademy.models.TeachersSideModels.getLectureModelItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class CreateAQuizRepo(val Api:Api) {
    private val CreateQuizLiveData = MutableLiveData<Response<CreateQuizModel>>()
    fun CreateAQuizApi(
        title: String,
        description: String,
        token: String
    ): MutableLiveData<Response<CreateQuizModel>> {
        val result = Api.createAQuiz(title, description, "Bearer ${token}")
        result.enqueue(object : Callback<CreateQuizModel?> {
            override fun onResponse(
                call: Call<CreateQuizModel?>,
                response: retrofit2.Response<CreateQuizModel?>
            ) {
                if (response.isSuccessful) {
                    CreateQuizLiveData.postValue(Response.Success(response.body()))

                } else {
                    CreateQuizLiveData.postValue(Response.Error(response.message().toString()))

                }
            }

            override fun onFailure(call: Call<CreateQuizModel?>, t: Throwable) {
                CreateQuizLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return CreateQuizLiveData
    }
}