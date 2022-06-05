package com.example.unacademy.Repository.TeachersSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.TeachersSideModels.UploadPdf.uploadpdf
import com.example.unacademy.models.TeachersSideModels.UploadPdf.uploadpdfItem
import com.example.unacademy.models.TeachersSideModels.getStoryModelItem
import retrofit2.Call
import retrofit2.Callback

class getPdfRepo(var Api:Api) {
    private val getPdfLiveData = MutableLiveData<Response<ArrayList<uploadpdfItem>>>()
    fun getPdfApi(
        series:Int,
        token: String
    ): MutableLiveData<Response<ArrayList<uploadpdfItem>>> {
        val result = Api.GetPDf(series,"Bearer ${token}")
       result.enqueue(object : Callback<uploadpdf?> {
           override fun onResponse(
               call: Call<uploadpdf?>,
               response: retrofit2.Response<uploadpdf?>
           ) {
               Log.d("asdasdasdasfsadaf",response.code().toString())
               when
               {
                   response.isSuccessful->getPdfLiveData.postValue(Response.Success(response.body()))
                   else->
                   {
                       getNewToken(Api).getToken()
                       getPdfApi(series,getNewToken.acessTOken.toString())
                   }
               }
           }

           override fun onFailure(call: Call<uploadpdf?>, t: Throwable) {
               getPdfLiveData.postValue(Response.Error(t.message.toString()))
           }
       })
        return getPdfLiveData
    }
}