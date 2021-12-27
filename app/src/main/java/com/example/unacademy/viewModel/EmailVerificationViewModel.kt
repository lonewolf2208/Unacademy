package com.example.unacademy.viewModel

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation

class EmailVerificationViewModel:ViewModel() {
    val email = MutableLiveData("")
    val helperText=MutableLiveData("Required")
    fun Validations(){
        if(!Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches())
        {
            helperText.value = "Invalid Email Adress"
        }
        if(email.value=="")
        {
            helperText.value = "Please Enter Your Email Adress"
        }
    }

}