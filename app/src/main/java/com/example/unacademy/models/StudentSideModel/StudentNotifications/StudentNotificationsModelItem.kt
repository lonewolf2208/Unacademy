package com.example.unacademy.models.StudentSideModel.StudentNotifications

data class StudentNotificationsModelItem(
    val date: String,
    val id: Int,
    val is_seen: Boolean,
    val message: String,
    val `receiver`: Int,
    val sender: Int,
    val subject: String
)