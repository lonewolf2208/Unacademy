package com.example.unacademy.Repository.StudentSideRepo

import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class DeleteWishlistRepo(var Api:Api)
{
    private val DeleteStudentWishlisRepoLiveData = MutableLiveData<Response<ResponseBody>>()
    fun DeletestudentStoryInfoApi(
        id:Int,
        token: String
    ): MutableLiveData<Response<ResponseBody>> {
        val result = Api.deleteWishlist(id, "Bearer ${token}")
        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    DeleteStudentWishlisRepoLiveData.postValue(Response.Success(response.body()))
                } else {
                    DeleteStudentWishlisRepoLiveData.postValue(
                        Response.Error(
                            response.message().toString()
                        )
                    )
                }

            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                DeleteStudentWishlisRepoLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return DeleteStudentWishlisRepoLiveData
    }
}