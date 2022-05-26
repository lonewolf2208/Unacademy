package com.example.unacademy.Repository.StudentSideRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.api.Api
import com.example.unacademy.models.StudentSideModel.SearchStudentSide.SearchStudentSideItem
import com.example.unacademy.models.StudentSideModel.getSearchCourseModel.SearchCourseModelItem
import retrofit2.Call
import retrofit2.Callback

class SearchCourseRepo(var Api:Api) {
    private val searchCourseRepoLiveData = MutableLiveData<Response<List<SearchCourseModelItem>>>()
    fun searchEducatorProfileApi(
        token:String,
        name:String
    ): MutableLiveData<Response<List<SearchCourseModelItem>>> {
        val result = Api.searchCourse(name,"Bearer ${token}")
        result.enqueue(object : Callback<List<SearchCourseModelItem>?> {
            override fun onResponse(
                call: Call<List<SearchCourseModelItem>?>,
                response: retrofit2.Response<List<SearchCourseModelItem>?>
            ) {
                Log.d("SJAkdasd1",response.code().toString())
                when
                {
                    response.isSuccessful->{searchCourseRepoLiveData.postValue(Response.Success(response.body()))}
                    else->{
                        getNewToken(Api).getToken()
                        searchEducatorProfileApi(getNewToken.acessTOken,name)
                    }
                }
            }

            override fun onFailure(call: Call<List<SearchCourseModelItem>?>, t: Throwable) {
                searchCourseRepoLiveData.postValue(Response.Error("Something went wrong !! Please try again"))
            }
        })
        return searchCourseRepoLiveData
    }
}