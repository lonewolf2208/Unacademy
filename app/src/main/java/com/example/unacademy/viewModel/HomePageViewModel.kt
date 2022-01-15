package com.example.unacademy.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomePageViewModel():ViewModel() {
    var batchName=MutableLiveData<String>()
    var description=MutableLiveData<String>()
    var lectureCount=MutableLiveData<String>()


}