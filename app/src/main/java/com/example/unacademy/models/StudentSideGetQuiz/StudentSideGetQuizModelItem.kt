package com.example.unacademy.models.StudentSideGetQuiz

data class StudentSideGetQuizModelItem(
    val description: String,
    val educator: Int,
    val educator_details: EducatorDetails,
    val duration:Int,
    val id: Int,
    val questions: Int,
    val  is_attempted:Boolean,
    val title: String
)