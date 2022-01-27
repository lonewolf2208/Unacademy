package com.example.unacademy.models.StudentSideModel.QuizResultRepo

data class QuizResultModelItem(
    val attempted_answer: Int,
    val correct_answer: Int,
    val id: Int,
    val is_correct: Boolean,
    val question: Question,
    val student: Int
)