package com.example.unacademy.api


import com.example.unacademy.models.AuthModels.LoginDataClass
import com.example.unacademy.models.AuthModels.Message
import com.example.unacademy.models.AuthModels.SignUpDataClass
import com.example.unacademy.models.QuizQuestionsModel.quizQuestionsModel
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.Question
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.QuizResultModelItem
import com.example.unacademy.models.StudentSideModel.SearchStudentSide.SearchStudentSideItem
import com.example.unacademy.models.StudentSideModel.StudentNotifications.StudentNotificationsModelItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.EducatorDetails
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.studentStories.StudentStoryInfoModelItem
import com.example.unacademy.models.StudentSideModel.StudentStory.studentStoryModelItem
import com.example.unacademy.models.StudentSideModel.getSearchCourseModel.SearchCourseModelItem
import com.example.unacademy.models.StudentSideModel.getStudentProfileModel.getStudentProfileModel
import com.example.unacademy.models.StudentSideModel.teachersProfileModel.teacher_profile_student_side
import com.example.unacademy.models.TeachersSideModels.CreateQuizModel.CreateQuizModel
import com.example.unacademy.models.TeachersSideModels.UploadPdf.uploadpdf

import com.example.unacademy.models.TeachersSideModels.getLectureModelItem
import com.example.unacademy.models.TeachersSideModels.getStoryModelItem
import com.example.unacademy.models.TeachersSideModels.getTeachersProfile.getTeachersProfileModel
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
import com.example.unacademy.models.tokenModel
import okhttp3.ResponseBody
import retrofit2.Call

import retrofit2.http.*
import kotlin.collections.ArrayList

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
    fun getSeries(@Header("Authorization") token: String): Call<List<getStudentSeriesItem>>

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

    @GET("/student/notification/")
    fun getStudentNotifications(@Header("Authorization") token: String):Call<List<StudentNotificationsModelItem>>

    @GET("/student/profile/")
    fun GetStudentProfile(@Header("Authorization") token: String):Call<getStudentProfileModel>

    @GET("/student/educator-profile/{educator_id}/")
    fun getTeachersProfileStudentSide(@Path("educator_id") id: Int,@Header("Authorization") token: String):Call<teacher_profile_student_side>

    @GET("/student/search/profile/{username}/")
    fun searchProfile(@Path("username") username:String,@Header("Authorization") token: String):Call<List<SearchStudentSideItem>>

    @GET("/student/search/series/{name}/")
    fun searchCourse(@Path("name")name:String,@Header("Authorization") token: String):Call<List<SearchCourseModelItem>>

    @FormUrlEncoded
    @PATCH("/student/profile/")
    fun UpdateStudentProfile( @Field("name") name: String,
                              @Field("gender") gender: String,
                              @Field("birth") birth: String,
                              @Field("picture") picture: String,
                              @Field("standard") standard: String,
                              @Field("mobile") mobile: Long,
                              @Field("bio") bio: String,
                              @Header("Authorization") token: String):Call<ResponseBody>

    @FormUrlEncoded
    @POST("/educator/attachments/{series}/")
    fun UploadPdf(@Path("series")series:Int,@Field("title")title:String,@Field("description")description: String,@Field("doc")doc:String,@Header("Authorization") token: String):Call<ResponseBody>

    @GET("/educator/attachments/{series}/")
    fun GetPDf(@Path("series")series: Int,@Header("Authorization") token: String):Call<uploadpdf>

    @GET("/educator/quiz/")
    fun getQuiz(@Header("Authorization") token: String):Call<List<StudentSideGetQuizModelItem>>

    @GET("/educator/quiz/{quiz_id}")
    fun getQuestions(@Path("quiz_id")quiz_id:Int,@Header("Authorization") token: String):Call<List<Question>>
}