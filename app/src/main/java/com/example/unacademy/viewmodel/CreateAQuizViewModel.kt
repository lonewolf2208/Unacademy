package com.example.unacademy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.TeachersSideRepo.CreateAQuizRepo
import com.example.unacademy.Repository.TeachersSideRepo.getStoryRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.TeachersSideModels.CreateQuizModel.CreateQuizModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class CreateAQuizViewModel:ViewModel() {
    var title=MutableLiveData<String>()
    var questions=MutableLiveData<String>()
    var duration=MutableLiveData<String>()
    var token :String=""
suspend fun CreateAQuiz(): MutableLiveData<Response<CreateQuizModel>> {
    val Api= RetrofitClient.init()
    var createAQuizRepo=CreateAQuizRepo(Api)
    val job =viewModelScope.launch {
        var AccessToken = Splash_Screen.readInfo("access").toString()
        token = AccessToken
    }
    job.join()
    var result = createAQuizRepo.CreateAQuizApi(title.value.toString(),questions.value.toString(),token)
    return result
}
}