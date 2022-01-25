package com.example.unacademy.Ui.StudentsSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterQuizTEachersSide
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentSelfStudyStudentSideBinding


class SelfStudyStudentSide : Fragment() {

    lateinit var binding:FragmentSelfStudyStudentSideBinding
    lateinit var adapterGetQuiz: RecyclerAdapterQuizTEachersSide
    private var layoutManager: RecyclerView.LayoutManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_self_study_student_side, container, false)
        layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.RecyclerAdapterSelfStudy.layoutManager=layoutManager
        adapterGetQuiz= RecyclerAdapterQuizTEachersSide(homePageStudentSide.totalQuiz)
        binding.RecyclerAdapterSelfStudy.adapter=adapterGetQuiz
        adapterGetQuiz.onClickListener(object : RecyclerAdapterQuizTEachersSide.ClickListener {
            override fun OnClick(position: Int) {
                homePageStudentSide.quizTitle = homePageStudentSide.totalQuiz!!.get(position).title.toString()
                homePageStudentSide.quizDescription =homePageStudentSide.totalQuiz!!.get(position).description.toString()
                homePageStudentSide.quizLectureCount =homePageStudentSide.totalQuiz!!.get(position).questions.toString()
                homePageStudentSide.quizid =homePageStudentSide.totalQuiz!!.get(position).id.toInt()
                findNavController().navigate(R.id.quizShowPageStudentSide)
            }
        })
        return binding.root
    }


}