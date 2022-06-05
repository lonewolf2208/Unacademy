package com.example.unacademy.models.StudentSideModel.QuizResultRepo

data class Question(
    val marks: Int,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val question: String,
    var answer:Int,
)