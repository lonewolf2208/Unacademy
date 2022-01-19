package com.example.unacademy.api


import com.example.unacademy.models.AuthModels.LoginDataClass
import com.example.unacademy.models.AuthModels.Message
import com.example.unacademy.models.AuthModels.SignUpDataClass
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import com.example.unacademy.models.TeachersSideModels.getLectureModelItem
import com.example.unacademy.models.TeachersSideModels.getStoryModel
import com.example.unacademy.models.TeachersSideModels.getTeachersProfile.getTeachersProfileModel
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
import com.example.unacademy.models.tokenModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit

import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("/user/login/")
    fun LoginApi(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginDataClass>

    @FormUrlEncoded
    @POST("/user/send-otp/")
    fun SignUpApi(
        @Field("email") email: String,
        @Field("name") name: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/user/verify-otp/")
    fun Otp(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): Call<Message>

    @FormUrlEncoded
    @POST("/user/sign-up/")
    fun Password(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/api/token/")
    fun getToken(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SignUpDataClass>

    @FormUrlEncoded
    @POST("/user/change-password/")
    fun passwordChange(
        @Field("email") email: String,
        @Field("new password") password: String
    ): Call<Message>

    @FormUrlEncoded
    @POST("/educator/create/")
    fun teachersProfile(
        @Field("name") name: String,
        @Field("mobile") mobile: Long,
        @Field("gender") gender: String,
        @Field("birth") birth: String,
        @Field("picture") picture: String,
        @Field("qual") qual: String,
        @Field("bio") bio: String,
        @Field("sample_video") sample_video: String,
        @Header("Authorization") token: String
    ): Call<teachersProfileDataClass>

    @FormUrlEncoded
    @POST("/educator/series/")
    fun createSeries(
        @Field("name") name: String,
        @Field("icon") icon: String,
        @Field("description") description: String,
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/api/token/refresh/")
    fun refreshToken(@Header("Authorization") token: String): Call<tokenModel>

    @GET("/educator/series/")
    fun getSeries(@Header("Authorization") token: String):Call<List<educatorSeriesModelItem>>

    @GET("/educator/create/")
    fun getTeachersProfile(@Header("Authorization") token: String):Call<getTeachersProfileModel>

    @GET("/educator/series/lecture/{series}/")
    fun getLectures(@Path("series")series:Int, @Header("Authorization") token: String):Call<List<getLectureModelItem>>

    @FormUrlEncoded
    @POST("/educator/series/lecture/{series}/")
    fun UploadLectures(@Path("series")series:Int,@Field("name")name:String,@Field("description")description:String,@Field("video")video:String,@Header("Authorization") token: String):Call<ResponseBody>


    @FormUrlEncoded
    @PUT("/educator/create/")
    fun ChangeteachersProfile(
        @Field("name") name: String,
        @Field("mobile") mobile: Long,
        @Field("gender") gender: String,
        @Field("birth") birth: String,
        @Field("picture") picture: String,
        @Field("qual") qual: String,
        @Field("bio") bio: String,
        @Field("sample_video") sample_video: String,
        @Header("Authorization") token: String
    ): Call<teachersProfileDataClass>

    @FormUrlEncoded
    @POST("/educator/story/")
    fun UploadStory(
        @Field("doc")doc:String,
        @Header("Authorization") token: String
    ):Call<ResponseBody>
    
    @GET("/educator/story/")
    fun getStory(
        @Header("Authorization") token: String
    ):Call<List<getStoryModel>>


}