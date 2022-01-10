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

 @Headers("Connection:close")
 @POST("/educator/create/")
 fun teachersProfile(@Body teachersProfileDataClass: teachersProfileDataClass,@Header("Authorization")token:String): Call<ResponseBody>
}