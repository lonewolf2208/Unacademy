package com.example.unacademy.Ui.StudentsSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterQuizTEachersSide
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentSelfStudyStudentSideBinding
import com.example.unacademy.viewmodel.viewmodelStudentside.SelfStudyStudentSideViewModel
import kotlinx.coroutines.launch


class SelfStudyStudentSide : Fragment() {

    lateinit var binding:FragmentSelfStudyStudentSideBinding
    lateinit var viewModel:SelfStudyStudentSideViewModel
    lateinit var adapterGetQuiz: RecyclerAdapterQuizTEachersSide
    private var layoutManager: RecyclerView.LayoutManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this)[SelfStudyStudentSideViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_self_study_student_side, container, false)
        binding.lifecycleOwner=this
        binding.selfStudyViewModel=viewModel
        binding.shimmerFrameLayoutSelfStudyStudentSide2.startShimmerAnimation()
        binding.shimmerFrameLayoutSelfStudyStudentSide1.startShimmerAnimation()
        binding.shimmerFrameLayoutSelfStudyStudentSide0.startShimmerAnimation()
        lifecycleScope.launch {
            var result=viewModel.getQuiz()
            result.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                        is Response.Success->
                        {
                            layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                            var shimmerFrameLayoutHomePageQuiz0=binding.shimmerFrameLayoutSelfStudyStudentSide0
                            shimmerFrameLayoutHomePageQuiz0?.stopShimmerAnimation()
                            shimmerFrameLayoutHomePageQuiz0?.visibility=View.INVISIBLE
                            var shimmerFrameLayoutHomePageQuiz1=binding.shimmerFrameLayoutSelfStudyStudentSide1
                            shimmerFrameLayoutHomePageQuiz1?.stopShimmerAnimation()
                            shimmerFrameLayoutHomePageQuiz1?.visibility=View.INVISIBLE
                            var shimmerFrameLayoutHomePageQuiz2=binding.shimmerFrameLayoutSelfStudyStudentSide2
                            shimmerFrameLayoutHomePageQuiz2?.stopShimmerAnimation()
                            shimmerFrameLayoutHomePageQuiz2?.visibility=View.INVISIBLE
                            binding.RecyclerAdapterSelfStudy.layoutManager=layoutManager
                            adapterGetQuiz= RecyclerAdapterQuizTEachersSide(it.data)
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
                        }
                    }
                })
        }

        return binding.root
    }


}