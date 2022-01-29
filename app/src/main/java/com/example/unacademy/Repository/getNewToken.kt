package com.example.unacademy.Repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.Api
import com.example.unacademy.models.tokenModel
import com.google.android.gms.common.util.concurrent.HandlerExecutor
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import java.util.function.LongFunction

class getNewToken(private val Api:Api) {
    private var getTokenLiveData :tokenModel?=null
    var refreshToken:String?=null
    var acessTOken:String?=null
     suspend fun getToken() {
        Log.d("TokewnExpired",refreshToken.toString())

        var result=Api.refreshToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTY0NDY5MTMzNCwiaWF0IjoxNjQzMzk1MzM0LCJqdGkiOiI4NDIxN2I0MWZjMTE0ODg5OTE3N2E5MmU3MDFiNWNjNyIsInVzZXJfaWQiOjN9.5H_TCkFbXuSxnxKAYcOwj3q8_563V9-AYpInuHmds74").enqueue(object : Callback<tokenModel?> {
            override fun onResponse(
                call: Call<tokenModel?>,
                response: retrofit2.Response<tokenModel?>
            ) {

                var dataStore: DataStore<Preferences>? = null
                if (response.isSuccessful)
                {
                    val job=MainScope().launch {
                        Log.w("JKFJLASFJKLAFJ","1stACCESS ::::: "+Splash_Screen.readInfo("access").toString())
                        Splash_Screen.saveInfo(
                            "access",
                            response.body()!!.access
                        )
                        Log.w("JKFJLASFJKLAFJ","2ndACCESS ::::: "+Splash_Screen.readInfo("access").toString())
                        Splash_Screen.saveInfo(
                            "refresh",
                            response.body()!!.refresh
                        )
                    }
                    suspend {
                        job.join()
                    }

                }

            }
            override fun onFailure(call: Call<tokenModel?>, t: Throwable) {
                Log.d("TokewnExpired",t.localizedMessage.toString())
            }
        })
    }
}