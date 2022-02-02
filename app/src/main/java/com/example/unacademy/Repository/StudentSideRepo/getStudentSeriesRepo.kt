package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class getStudentSeriesRepo(val Api:Api) {
    private val getStudentSeriesLiveData = MutableLiveData<Response<List<getStudentSeriesItem>>>()
    fun studentSeriesApi(
        token: String
    ): MutableLiveData<Response<List<getStudentSeriesItem>>> {
        val result = Api.studentSeries("Bearer ${token}")
        result.enqueue(object : Callback<List<getStudentSeriesItem>?> {
            override fun onResponse(
                call: Call<List<getStudentSeriesItem>?>,
                response: retrofit2.Response<List<getStudentSeriesItem>?>
            ) {
                when
                {
                    response.isSuccessful->getStudentSeriesLiveData.postValue(Response.Success(response.body()))
                    response.code()==403->getStudentSeriesLiveData.postValue(Response.TokenExpire())
                    else ->
                    {
                        getNewToken(Api).getToken()
                        studentSeriesApi(getNewToken.acessTOken.toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<getStudentSeriesItem>?>, t: Throwable) {
                getStudentSeriesLiveData.postValue(Response.Error(t.message.toString()))
            }
        })
        return getStudentSeriesLiveData
    }
}
