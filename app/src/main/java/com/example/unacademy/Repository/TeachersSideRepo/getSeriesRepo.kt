package com.example.unacademy.Repository.TeachersSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.Api
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class getSeriesRepo(val Api:Api){
        private val getSeriesLiveData = MutableLiveData<Response<List<educatorSeriesModelItem>>>()
        fun getSeriesApi(
            token: String
        ): MutableLiveData<Response<List<educatorSeriesModelItem>>> {

            val result = Api.getSeries(
                "Bearer ${token}"
            )
            result.enqueue(object : Callback<List<educatorSeriesModelItem>?> {
                override fun onResponse(
                    call: Call<List<educatorSeriesModelItem>?>,
                    response: retrofit2.Response<List<educatorSeriesModelItem>?>
                ) {
                    Log.d("ddsadasd",response.message().toString())
                    when
                    {
                        response.isSuccessful->getSeriesLiveData.postValue(Response.Success(response.body()))
                        else->
                        {
                            MainScope().launch {
                                Log.w(
                                    "OTKENSDASDASD",
                                    "Before+++++++" + Splash_Screen.readInfo("access").toString()
                                )
                            }
                            getNewToken(Api).getToken()
                            MainScope().launch {
                                Log.w(
                                    "OTKENSDASDASD",
                                    "After:::::::::" +getNewToken.acessTOken
                                )
                            }
                            getSeriesApi(getNewToken.acessTOken.toString())
                        }

                    }
                }

                override fun onFailure(call: Call<List<educatorSeriesModelItem>?>, t: Throwable) {
                    getSeriesLiveData.postValue(Response.Error("on Failure"))
                }
            })
            return getSeriesLiveData
        }
}
