package com.example.unacademy.Adapter.StudentSideAdapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.unacademy.Ui.StudentsSide.CoursesSearchStudentSide
import com.example.unacademy.Ui.StudentsSide.EducatorSearchStudentSide
import com.example.unacademy.Ui.StudentsSide.QuizShowPageTeachersProfile
import com.example.unacademy.Ui.StudentsSide.SeriesShowPageTeacherProfile

class SearchToggleAdapter(fragment:Fragment):FragmentStateAdapter(fragment) {
    companion object
    {
        var educatorOpen=true
        var courseOpen=false
    }
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment {
        return when(position)
        {
            0->
            {
                educatorOpen=true
                courseOpen=false
               EducatorSearchStudentSide()
            }
            1->
            {
                educatorOpen=false
                courseOpen=true
                CoursesSearchStudentSide()
            }
            else->
            {
                EducatorSearchStudentSide()
            }
        }
    }
}