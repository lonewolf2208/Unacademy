package com.example.unacademy.models.QuizQuestionsModel

data class quizQuestionsModel(
    val answer: Int,
    val id: Int,
    val marks: Int,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val question: String,
    val quiz: Int
)