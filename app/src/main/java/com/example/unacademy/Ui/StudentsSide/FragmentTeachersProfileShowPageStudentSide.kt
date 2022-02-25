package com.example.unacademy.Ui.StudentsSide

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.unacademy.Adapter.StudentSideAdapters.FragmentStateChangeAdapter
import com.example.unacademy.R
import com.example.unacademy.Repository.StudentSideRepo.TeacherProfileRepoStudentSide
import com.example.unacademy.databinding.FragmentTeachersProfileShowPageStudentSideBinding
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.models.StudentSideModel.teachersProfileModel.teacher_profile_student_side
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import com.example.unacademy.viewmodel.viewmodelStudentside.TeacherProfileShowStudentSideViewModel
import com.google.android.gms.common.api.Response
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class FragmentTeachersProfileShowPageStudentSide : Fragment() {

    lateinit var binding:FragmentTeachersProfileShowPageStudentSideBinding
    lateinit var viewModel:TeacherProfileShowStudentSideViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this)[TeacherProfileShowStudentSideViewModel::class.java]
    }
    companion object
    {
        var teacher_profile_series:List<getStudentSeriesItem>?=null
        var teacher_profile_quizzes:List<StudentSideGetQuizModelItem>?=null
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=DataBindingUtil.inflate(inflater,
            R.layout.fragment_teachers_profile_show_page_student_side,
            container,
            false
        )
        binding.lifecycleOwner=this
        binding.teacherProfileModel=viewModel
        lifecycleScope.launch {
            var result=viewModel.getProfile(homePageStudentSide.teacher_id)
            result.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                       is com.example.unacademy.Repository.Response.Success ->
                       {
                           binding.shapeableImageView2.load(it.data?.picture)
                           binding.TeacherProfileShowPageSeriesCount.text= it.data!!.educator_series.size.toString()
                           binding.QuizzesShowPageTeacherProfileShowPage.text=it.data.educator_quiz.size.toString()
                           binding.TeacherNameTeacherProfile.text=it.data.name
                           binding.TeacherDescriptionTeacherProfileShowPage.text=it.data.qual
                           teacher_profile_series=it.data.educator_series
                           teacher_profile_quizzes=it.data.educator_quiz
                       }
                    }
                })

        }
        binding.viewPager.adapter=FragmentStateChangeAdapter(this@FragmentTeachersProfileShowPageStudentSide)
        TabLayoutMediator(binding.tabLayout,binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Quizzes"
                1 -> tab.text = "Series"
            }
        }.attach()

        return binding.root
    }


}