package com.example.unacademy.models.TeachersSideModels.educatorSeries

import com.example.unacademy.models.TeachersSideModels.educatorSeries.EducatorDetails

data class educatorSeriesModelItem(
    val description: String,
    val educator: Int,
    val educator_details: EducatorDetails,
    val icon: String,
    val id: Int,
    val name: String
)