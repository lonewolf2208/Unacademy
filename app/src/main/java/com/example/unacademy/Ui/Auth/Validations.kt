package com.example.unacademy.Ui.Auth

import android.util.Patterns

object Validations {

    fun emailValdiation(email:String):String?
    {
        if(email.isNullOrEmpty())
        {
            return "Please enter your email"
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return "Invalid Email Adress"
        }

        return null
    }

    fun validPassword(passwordText:String): String?
    {

        if(passwordText.length < 8)
        {
            return "Minimum 8 Character Password"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Must Contain 1 Upper-case Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Must Contain 1 Lower-case Character"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }

        return null
    }
    fun NameValidations(name : String) :String?
    {
        if(name.isNullOrEmpty())
        {
            return "Required"
        }
        return null
    }
}