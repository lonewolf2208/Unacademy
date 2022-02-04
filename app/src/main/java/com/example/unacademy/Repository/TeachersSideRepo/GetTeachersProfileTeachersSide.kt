package com.example.unacademy.Repository.TeachersSideRepo

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.TeachersSideModels.getStoryModelItem
import com.example.unacademy.models.TeachersSideModels.getTeachersProfile.getTeachersProfileModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class GetTeachersProfileTeachersSide(val Api:Api)
{
    private val getTeachersProfileLiveData = MutableLiveData<Response<getTeachersProfileModel>>()
    fun getTeachersPRofileApi(
        token: String
    ): MutableLiveData<Response<getTeachersProfileModel>> {
        val result = Api.getTeachersProfile( "Bearer ${token}")
        result.enqueue(object :
            Callback<getTeachersProfileModel?> {
            override fun onResponse(
                call: Call<getTeachersProfileModel?>,
                response: retrofit2.Response<getTeachersProfileModel?>
            ) {
                if(response.isSuccessful)
                {
                    getTeachersProfileLiveData.postValue(Response.Success(response.body()))
                }
                else
                {
                   getNewToken(Api)
                    getTeachersPRofileApi(getNewToken.acessTOken)
                }
            }

            override fun onFailure(call: Call<getTeachersProfileModel?>, t: Throwable) {
                getTeachersProfileLiveData.postValue(Response.Error(t.message.toString()))
            }
        })
        return getTeachersProfileLiveData
    }
}