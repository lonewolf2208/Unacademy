package com.example.unacademy.Repository.StudentSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.getStudentSeries.studentStories.StudentStoryInfoModelItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class StudentWishlisRepo(var Api:Api)
{
    private val StudentWishlisRepoLiveData = MutableLiveData<Response<ResponseBody>>()
    fun StudentWishlistApi(
        id:Int,
        token: String
    ): MutableLiveData<Response<ResponseBody>> {
        val result = Api.studentWishlist(id, "Bearer ${token}")
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                Log.w("ddsadasd",response.raw().code.toString())
               when
               {
                   response.isSuccessful->StudentWishlisRepoLiveData.postValue(Response.Success(response.body()))
                   else->
                   {
                       getNewToken(Api).getToken()
                       StudentWishlistApi(id, getNewToken.acessTOken.toString())
                   }
               }

            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                StudentWishlisRepoLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return StudentWishlisRepoLiveData
    }
}
