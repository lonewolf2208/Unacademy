package com.example.unacademy.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.TeachersSideRepo.getSeriesRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import com.example.unacademy.models.TeachersSideModels.getStoryModelItem
import kotlinx.coroutines.launch

class GetStoryViewModel:ViewModel()
{
    var token:String=""
    suspend fun getStory(): MutableLiveData<Response<List<educatorSeriesModelItem>>> {
        val Api=RetrofitClient.init()
        var getStoryRepo=getSeriesRepo(Api)
        val job =viewModelScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        job.join()
        var result = getStoryRepo.getSeriesApi(token)
        return result
    }
}
