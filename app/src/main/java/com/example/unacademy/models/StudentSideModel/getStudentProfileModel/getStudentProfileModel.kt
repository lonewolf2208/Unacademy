package com.example.unacademy.models.StudentSideModel.getStudentProfileModel

data class getStudentProfileModel(
    val bio: String,
    val birth: String,
    val following: List<Following>,
    val gender: String,
    val id: Int,
    val mobile: Long,
    val name: String,
    val picture: String,
    val standard: String,
    val student: Student,
    val wishlist: List<Int>
)