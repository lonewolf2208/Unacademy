package com.example.unacademy.Repository.TeachersSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class getSeriesRepo(val Api:Api){
        private val getSeriesLiveData = MutableLiveData<Response<List<getStudentSeriesItem>>>()
        fun getSeriesApi(
            token: String
        ): MutableLiveData<Response<List<getStudentSeriesItem>>> {

            val result = Api.getSeries(
                "Bearer ${token}"
            )
            result.enqueue(object : Callback<List<getStudentSeriesItem>?> {
                override fun onResponse(
                    call: Call<List<getStudentSeriesItem>?>,
                    response: retrofit2.Response<List<getStudentSeriesItem>?>
                ) {
                    Log.d("ddsadasd",response.message().toString())
                    when
                    {
                        response.isSuccessful->getSeriesLiveData.postValue(Response.Success(response.body()))
                        else->
                        {
                            getNewToken(Api).getToken()
                            getSeriesApi(getNewToken.acessTOken.toString())
                        }

                    }
                }

                override fun onFailure(call: Call<List<getStudentSeriesItem>?>, t: Throwable) {
                    getSeriesLiveData.postValue(Response.Error("on Failure"))
                }
            })
            return getSeriesLiveData
        }
}
