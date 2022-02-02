package com.example.unacademy.Repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.Api
import com.example.unacademy.models.tokenModel
import com.example.unacademy.viewmodel.HomePageViewModel
import com.google.android.gms.common.util.concurrent.HandlerExecutor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import java.util.function.LongFunction

class getNewToken(private val Api:Api) {
    companion object
    {
        var acessTOken=""
    }
    var refreshToken:String?=null

    fun getToken(): String {
         Log.d("TokewnExpired", refreshToken.toString())
             Api.refreshToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTY0NDY5MTMzNCwiaWF0IjoxNjQzMzk1MzM0LCJqdGkiOiI4NDIxN2I0MWZjMTE0ODg5OTE3N2E5MmU3MDFiNWNjNyIsInVzZXJfaWQiOjN9.5H_TCkFbXuSxnxKAYcOwj3q8_563V9-AYpInuHmds74")
                 .enqueue(object : Callback<tokenModel?> {
                     override fun onResponse(
                         call: Call<tokenModel?>,
                         response: retrofit2.Response<tokenModel?>
                     ) {
                         Log.w("RESSESES", response.body()!!.access.toString())
                         if (response.isSuccessful) {
                             acessTOken= response.body()!!.access.toString()
                             GlobalScope.launch {
                                 Splash_Screen.saveInfo(
                                     "access",
                                     response.body()!!.access.toString()
                                 )
                             }
                         }
                     }
                     override fun onFailure(call: Call<tokenModel?>, t: Throwable) {
                         Log.d("TokewnExpired", t.localizedMessage.toString())
                     }
                 })
        return acessTOken
    }
}