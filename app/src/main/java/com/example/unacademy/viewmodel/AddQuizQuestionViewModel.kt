package com.example.unacademy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.TeachersSideRepo.UploadQuizQuestionRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.Ui.Auth.Validations
import com.example.unacademy.api.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class AddQuizQuestionViewModel:ViewModel()
{
    var question=MutableLiveData<String>()
    var option1=MutableLiveData<String>()
    var option2=MutableLiveData<String>()
    var option3= MutableLiveData<String>()
    var option4=MutableLiveData<String>()
    var answer=MutableLiveData<String>()
    var helperTextQuestion=MutableLiveData<String>()
    var helperTextOption1=MutableLiveData<String>()
    var helperTextOption2=MutableLiveData<String>()
    var helperTextOption3=MutableLiveData<String>()
    var helperTextOption4=MutableLiveData<String>()
    var helperTextAnswer=MutableLiveData<String>()

    var token:String=""
    suspend fun UploadQuizQuestion(): MutableLiveData<Response<ResponseBody>> {
        var Api=RetrofitClient.init()
        var uploadQuizQuestionRepo=UploadQuizQuestionRepo(Api)
        val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var result=uploadQuizQuestionRepo.uploadQuizQuestionApi(question.value.toString(),4, option1.value.toString(),option2.value.toString(),option3.value.toString(),option4.value.toString(),
            answer.value!!.toInt(),token)
        return result
    }

    fun Validations() : Unit?{
        if (question.value.isNullOrEmpty()) {
            helperTextQuestion.postValue("Enter the Question")
        }
        if (option1.value.isNullOrEmpty()) {
            helperTextOption1.postValue("Enter Option 1")
        }
        if (option2.value.isNullOrEmpty()) {
            helperTextOption2.postValue("Enter Option 2")
        }
        if (option3.value.isNullOrEmpty()) {
            helperTextOption3.postValue("Enter Option 3")
        }
        if (option4.value.isNullOrEmpty())
        {helperTextOption4.postValue("Enter Option 4")}
        if(answer.value.isNullOrEmpty() || answer.value=="Select Your Options")
        {helperTextAnswer.postValue("Select Your Answer")}
        else
        {
            return null
        }
        return Unit
    }
}