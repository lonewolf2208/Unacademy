package com.example.unacademy.api


import com.example.unacademy.Ui.Auth.SignUp.Companion.email
import com.example.unacademy.models.LoginDataClass
import com.example.unacademy.models.Message
import com.example.unacademy.models.SignUpDataClass
import okhttp3.ResponseBody
import retrofit2.Call

import retrofit2.Response
import retrofit2.http.*

interface Api {

 @FormUrlEncoded
 @POST("/user/login/")
 fun LoginApi(
  @Field("email") email: String,
  @Field("password") password: String
 ): Call<ResponseBody>

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
 fun getToken
          (
   @Field("email") email:String,
   @Field("password")password: String
 ):Call<SignUpDataClass>

 @FormUrlEncoded
 @POST("/user/change-password/")
 fun passwordChange(
  @Field("email")email:String,
  @Field("new password")password: String
 ):Call<Message>
}