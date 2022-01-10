package com.example.unacademy.models.AuthModels

data class LoginDataClass(
    val email: String,
    val is_educator: Boolean,
    val is_student: Boolean,
    val name: String
)