package com.example.unacademy.viewModel

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.unacademy.R
import kotlin.coroutines.coroutineContext

class LogInViewModel()  : ViewModel() {
    var loginEmail = MutableLiveData<String>()
    var Password=MutableLiveData<String>()
    var HelperText=MutableLiveData<String>("Required")
    var HelperTextPassword=MutableLiveData<String>("Required")

    fun Validations():Boolean{
        if(!Patterns.EMAIL_ADDRESS.matcher(loginEmail.value.toString()).matches())
        {
            HelperText.value = "Invalid Email Adress"
            return false
        }
        if(loginEmail.value.isNullOrEmpty())
        {
            HelperText.value= "Email Can't be Empty"
            return false
        }
        if(Password.value.isNullOrEmpty())
        {
            HelperTextPassword.value="Please Enter Your Password"
            return false
        }
//        view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.signUp) }
        return true
    }

}