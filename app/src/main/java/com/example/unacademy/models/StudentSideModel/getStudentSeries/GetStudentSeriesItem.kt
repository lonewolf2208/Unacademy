package com.example.unacademy.models.StudentSideModel.getStudentSeries

import com.example.unacademy.models.StudentSideModel.getStudentSeries.EducatorDetails

data class getStudentSeriesItem(
    val description: String,
    val educator: Int,
    val educator_details: EducatorDetails,
    val icon: String,
    val lectures:Int,
    val is_wishlisted:Boolean,
    val id: Int,
    val name: String
)