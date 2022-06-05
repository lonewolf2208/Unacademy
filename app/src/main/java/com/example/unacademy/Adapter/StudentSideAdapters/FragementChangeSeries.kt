package com.example.unacademy.Adapter.StudentSideAdapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.unacademy.Ui.PdfShowPage
import com.example.unacademy.Ui.StudentsSide.QuizShowPageTeachersProfile
import com.example.unacademy.Ui.StudentsSide.SeriesShowPageTeacherProfile
import com.example.unacademy.Ui.TeachersSide.Lectures

class FragementChangeSeries(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment
    {
        return when(position)
        {
            0->
            {
               Lectures()
            }
            1->
            {
               PdfShowPage()
            }
            else->
            {
                Lectures()
            }
        }

    }
}