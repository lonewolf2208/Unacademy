package com.example.unacademy.api


import com.example.unacademy.models.AuthModels.LoginDataClass
import com.example.unacademy.models.AuthModels.Message
import com.example.unacademy.models.AuthModels.SignUpDataClass
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
import okhttp3.ResponseBody
import retrofit2.Call

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
 fun teachersProfile(@Field("name")name: String,@Field("mobile")mobile:Double, @Field("gender")gender:String, @Field("birth")birth:String, @Field("picture")picture:String, @Field("qual")qual:String, @Field("bio")bio:String, @Field("sample_video")sample_video:String, @Header("Authorization") token:String): Call<ResponseBody>

@FormUrlEncoded
 @POST("/educator/series/")
 fun createSeries(@Field("name")name: String,@Field("icon")icon:String,@Field("description")description:String,@Header("Authorization")token:String):Call<ResponseBody>


 @FormUrlEncoded
 @POST("/api/token/refresh/")
 fun refreshToken(@Header("Authorization")token:String):Call<ResponseBody>
}