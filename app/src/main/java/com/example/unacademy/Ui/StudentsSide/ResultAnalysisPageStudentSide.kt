package com.example.unacademy.Ui.StudentsSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterLatestSeries
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterResultAnalysisStudentSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterStudentStory
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentResultAnalysisPageStudentSideBinding


class ResultAnalysisPageStudentSide : Fragment() {
   lateinit var binding:FragmentResultAnalysisPageStudentSideBinding
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: RecyclerAdapterResultAnalysisStudentSide
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,
            R.layout.fragment_result_analysis_page_student_side,
            container,
            false
        )
        binding.QuizHEadingREsultAnalysis.text=homePageStudentSide.quizTitle.toString()
        layoutManager= LinearLayoutManager(container?.context)
        binding.RecyclerAdapterResultAnalysisStudentSide.layoutManager=layoutManager
        adapter= RecyclerAdapterResultAnalysisStudentSide(QuizResultPage.quizresult)
        binding.RecyclerAdapterResultAnalysisStudentSide.adapter=adapter
        return binding.root
    }


}