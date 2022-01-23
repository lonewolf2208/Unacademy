package com.example.unacademy.models.TeachersSideModels.CreateQuizModel

data class CreateQuizModel(
    val description: String,
    val educator: Int,
    val educator_details: EducatorDetails,
    val id: Int,
    val questions: Int,
    val title: String
)