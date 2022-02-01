package com.example.unacademy.Repository.TeachersSideRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.models.AuthModels.Message
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class ChangePasswordInsideRepo(var Api:Api) {
    private var ChangeePasswordInsideRepoLiveData = MutableLiveData<Response<String>>()
    fun ChangePasswordInsideApi(old_password: String, new_password: String,token:String): MutableLiveData<Response<String>> {
        val result = Api.changePasswordInside(old_password, new_password,"Bearer ${token}")

        result.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    ChangeePasswordInsideRepoLiveData.postValue(
                        Response.Success(
                            response.code().toString()
                        )
                    )

                } else {
                    ChangeePasswordInsideRepoLiveData.postValue(
                        Response.Error(
                            response.code().toString()
                        )
                    )

                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                ChangeePasswordInsideRepoLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return ChangeePasswordInsideRepoLiveData
    }
}