package com.example.unacademy.models.TeachersSideModels.getTeachersProfile

import com.example.unacademy.models.TeachersSideModels.getTeachersProfile.Educator

data class getTeachersProfileModel(
    val attachment: Any,
    val bio: String,
    val birth: String,
    val educator: Educator,
    val gender: String,
    val id: Int,
    val mobile: Long,
    val name: String,
    val picture: String,
    val qual: String,
    val sample_video: String
)