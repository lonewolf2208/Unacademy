package com.example.unacademy.Repository.StudentSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.SearchStudentSide.SearchStudentSideItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import retrofit2.Call
import retrofit2.Callback

class SearchProfileStudentSide(var Api:Api) {
    private val searchProfileRepoLiveData = MutableLiveData<Response<List<SearchStudentSideItem>>>()
    fun searchEducatorProfileApi(
        token: String,
        username:String
    ): MutableLiveData<Response<List<SearchStudentSideItem>>> {
        val result = Api.searchProfile(username,"Bearer ${token}")
        result.enqueue(object : Callback<List<SearchStudentSideItem>?> {
            override fun onResponse(
                call: Call<List<SearchStudentSideItem>?>,
                response: retrofit2.Response<List<SearchStudentSideItem>?>
            ) {
                searchProfileRepoLiveData.postValue(Response.Loading())
                Log.d("SJAkdasd",response.code().toString())
                when
                {
                    response.isSuccessful->searchProfileRepoLiveData.postValue(Response.Success(response.body()))
                    else->{
                        getNewToken(Api).getToken()
                        searchEducatorProfileApi(getNewToken.acessTOken,username)
                    }
                }
            }

            override fun onFailure(call: Call<List<SearchStudentSideItem>?>, t: Throwable) {
                searchProfileRepoLiveData.postValue(Response.Error(t.localizedMessage.toString()))
            }
        })
        return searchProfileRepoLiveData
    }
}