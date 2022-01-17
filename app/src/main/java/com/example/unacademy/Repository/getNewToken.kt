package com.example.unacademy.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.Api
import com.example.unacademy.models.tokenModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class getNewToken(private val Api:Api) {
    private var getTokenLiveData :tokenModel?=null
    var refreshToken:String?=null
    fun getToken() {
    MainScope().launch {
        refreshToken= Splash_Screen.readInfo("refresh")
    }
        val result=Api.refreshToken(refreshToken.toString()).enqueue(object : Callback<tokenModel?> {
            override fun onResponse(
                call: Call<tokenModel?>,
                response: retrofit2.Response<tokenModel?>
            ) {
                if (response.isSuccessful) {
                    MainScope().launch {
                        Splash_Screen.saveInfo(
                            "access",
                            response.body()!!.access
                        )
                        Splash_Screen.saveInfo(
                            "refresh",
                            response.body()!!.refresh
                        )
                        getTokenLiveData = response.body()
                    }
                }
            }
            override fun onFailure(call: Call<tokenModel?>, t: Throwable) {

            }
        })
    }
}