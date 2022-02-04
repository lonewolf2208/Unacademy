package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.getStudentSeries.EducatorDetails
import retrofit2.Call
import retrofit2.Callback

class OurEducatorRepo(var Api:Api) {
    private val ourEducatorLiveData = MutableLiveData<Response<List<EducatorDetails>>>()
    fun ourEducatorApi(
        token: String
    ): MutableLiveData<Response<List<EducatorDetails>>> {
        val result = Api.ourEducators("Bearer ${token}")
       result.enqueue(object : Callback<List<EducatorDetails>?> {
           override fun onResponse(
               call: Call<List<EducatorDetails>?>,
               response: retrofit2.Response<List<EducatorDetails>?>
           ) {
               when
               {
                   response.isSuccessful->ourEducatorLiveData.postValue(Response.Success(response.body()))
                   else->
                   {
                       getNewToken(Api).getToken()
                      ourEducatorApi(getNewToken.acessTOken.toString())
                   }
               }

           }

           override fun onFailure(call: Call<List<EducatorDetails>?>, t: Throwable) {
               ourEducatorLiveData.postValue(Response.Error(t.toString()))
           }
       })
        return ourEducatorLiveData
    }
}
