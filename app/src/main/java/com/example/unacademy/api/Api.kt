package com.example.unacademy.api


import com.example.unacademy.models.AuthModels.LoginDataClass
import com.example.unacademy.models.AuthModels.Message
import com.example.unacademy.models.AuthModels.SignUpDataClass
import com.example.unacademy.models.QuizQuestionsModel.quizQuestionsModel
import com.example.unacademy.models.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.QuizResultModelItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.EducatorDetails
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.studentStories.StudentStoryInfoModelItem
import com.example.unacademy.models.StudentStory.studentStoryModelItem
import com.example.unacademy.models.TeachersSideModels.CreateQuizModel.CreateQuizModel
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import com.example.unacademy.models.TeachersSideModels.getLectureModelItem
import com.example.unacademy.models.TeachersSideModels.getStoryModelItem
import com.example.unacademy.models.TeachersSideModels.getTeachersProfile.getTeachersProfileModel
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
import com.example.unacademy.models.tokenModel
import com.example.unacademy.viewmodel.CreateAQuizViewModel
import okhttp3.ResponseBody
import retrofit2.Call

import retrofit2.http.*
import java.util.ArrayList

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
    fun refreshToken(
        @Field("refresh")refresh:String
    ): Call<tokenModel>

    @GET("/educator/series/")
    fun getSeries(@Header("Authorization") token: String): Call<List<educatorSeriesModelItem>>

    @GET("/educator/create/")
    fun getTeachersProfile(@Header("Authorization") token: String): Call<getTeachersProfileModel>

    @GET("/educator/series/lecture/{series}/")
    fun getLectures(
        @Path("series") series: Int,
        @Header("Authorization") token: String
    ): Call<List<getLectureModelItem>>

    @FormUrlEncoded
    @POST("/educator/series/lecture/{series}/")
    fun UploadLectures(
        @Path("series") series: Int,
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("video") video: String,
        @Header("Authorization") token: String
    ): Call<ResponseBody>


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
        @Field("doc") doc: String,
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    @GET("/educator/story/")
    fun getStory(
        @Header("Authorization") token: String
    ): Call<List<getStoryModelItem>>

    @FormUrlEncoded
    @POST("/student/create/")
    fun createStudent(
        @Field("name") name: String,
        @Field("gender") gender: String,
        @Field("birth") birth: String,
        @Field("picture") picture: String,
        @Field("standard") standard: String,
        @Field("mobile") mobile: Long,
        @Field("bio") bio: String,
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    @GET("/student/series/")
    fun studentSeries(
        @Header("Authorization") token: String
    ): Call<List<getStudentSeriesItem>>

    @GET("/student/educator-list/")
    fun ourEducators(
        @Header("Authorization") token: String
    ): Call<List<EducatorDetails>>

    @GET("/student/story-users/")
    fun studentStoryProfile(
        @Header("Authorization") token: String
    ): Call<ArrayList<studentStoryModelItem>>

    @GET("/student/story/{educator_id}")
    fun getStudentStory(
        @Path("educator_id") id: Int, @Header("Authorization") token: String
    ): Call<List<StudentStoryInfoModelItem>>


    @FormUrlEncoded
    @POST("/educator/quiz/")
    fun createAQuiz(
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("duration")duration:Int,
        @Header("Authorization") token: String
    ): Call<CreateQuizModel>

    @FormUrlEncoded
    @POST("/educator/quiz/question/")
    fun UploadQuizQuestion(
        @Field("quiz") quiz: Int,
        @Field("question") question: String,
        @Field("marks") marks: Int,
        @Field("option1") option1: String,
        @Field("option2") option2: String,
        @Field("option3") option3: String,
        @Field("option4") option4: String,
        @Field("answer") answer: Int,
        @Header("Authorization") token: String
    ): Call<ResponseBody>


    @FormUrlEncoded
    @PUT("/student/profile/")
    fun addFollowing(
        @Field("following") following: Int,
        @Header("Authorization") token: String
    ): Call<ResponseBody>


    @GET("/student/quiz/")
    fun getQuizStudentSide(@Header("Authorization") token: String): Call<List<StudentSideGetQuizModelItem>>

    @GET("/educator/quiz/{quiz_id}/")
    fun getQuizQuestions(
        @Path("quiz_id") id: Int,
        @Header("Authorization") token: String
    ): Call<List<quizQuestionsModel>>

    @FormUrlEncoded
    @POST("/student/quiz/question/attempt/")
    fun UploadQuestionAns(
        @Field("question") question: Int,
        @Field("answer") answer: Int, @Header("Authorization") token: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @PUT("/student/wishlist/")
    fun studentWishlist(
        @Field("series") series: Int,
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    @GET("/student/wishlist/")
    fun getWishlistedSeries(
        @Header("Authorization") token: String
    ): Call<ArrayList<getStudentSeriesItem>>


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/student/wishlist/", hasBody = true)
    fun deleteWishlist(
        @Field("series") series: Int,
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @PUT("/student/profile/")
    fun removeFollowing(
        @Field("remove") remove: Int,
        @Header("Authorization") token: String
    ): Call<ResponseBody>


    @GET("/student/quiz/{quiz_id}/analysis/")
    fun getQuizResult(
        @Path("quiz_id") id: Int,
        @Header("Authorization") token: String
    ): Call<List<QuizResultModelItem>>


    @FormUrlEncoded
    @POST("/user/change-password/")
    fun changePasswordInside(
        @Field("old_password")password:String,
        @Field("new_password")new_password:String,
        @Header("Authorization") token: String
    ):Call<ResponseBody>
}