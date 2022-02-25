package com.example.unacademy.models.StudentSideModel.teachersProfileModel

import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem

data class teacher_profile_student_side(
    val attachment: Any,
    val bio: String,
    val birth: String,
    val educator: Educator,
    val educator_quiz: List<StudentSideGetQuizModelItem>,
    val educator_series: List<getStudentSeriesItem>,
    val gender: String,
    val id: Int,
    val mobile: Long,
    val name: String,
    val picture: String,
    val qual: String,
    val sample_video: String
)