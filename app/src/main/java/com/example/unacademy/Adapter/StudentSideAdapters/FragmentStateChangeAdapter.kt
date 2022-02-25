package com.example.unacademy.Adapter.StudentSideAdapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.unacademy.Ui.StudentsSide.QuizShowPageTeachersProfile
import com.example.unacademy.Ui.StudentsSide.SeriesShowPageTeacherProfile

class FragmentStateChangeAdapter(fragment:Fragment):FragmentStateAdapter(fragment)
{
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment
    {
        return when(position)
        {
            0->
            {
                QuizShowPageTeachersProfile()
            }
            1->
            {
                SeriesShowPageTeacherProfile()
            }
            else->
            {
                QuizShowPageTeachersProfile()
            }
        }

    }
}