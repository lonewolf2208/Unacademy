package com.example.unacademy.Repository.TeachersSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
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
                    Log.d("jdiosjifusdoifs",token.toString())
                    when
                    {
                        response.isSuccessful->getSeriesLiveData.postValue(Response.Success(response.body()))
                        else->
                        {
                            getSeriesLiveData.postValue(Response.TokenExpire())
                            MainScope().launch {
                                getNewToken(RetrofitClient.init()).getToken()
                            }
                        }
                    }

                }

                override fun onFailure(call: Call<List<educatorSeriesModelItem>?>, t: Throwable) {
                    getSeriesLiveData.postValue(Response.Error(t.message.toString()))
                }
            })
            return getSeriesLiveData
        }
}
