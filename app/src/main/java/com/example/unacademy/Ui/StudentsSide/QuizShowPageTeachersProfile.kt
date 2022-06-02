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
import com.example.unacademy.Repository.StudentSideRepo.TeacherProfileRepoStudentSide
import com.example.unacademy.databinding.FragmentQuizShowPageTeachersProfileBinding
import com.example.unacademy.viewmodel.viewmodelStudentside.QuizShowPageTeachersProfileViewModel
import kotlinx.coroutines.launch


class QuizShowPageTeachersProfile : Fragment() {

    lateinit var binding:FragmentQuizShowPageTeachersProfileBinding
    lateinit var viewModel:QuizShowPageTeachersProfileViewModel
    lateinit var adapterGetQuiz: RecyclerAdapterQuizTEachersSide
    private var layoutManager: RecyclerView.LayoutManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this)[QuizShowPageTeachersProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_quiz_show_page_teachers_profile,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.quizShowPageTeachersProfileViewModel = viewModel

        lifecycleScope.launch {
            var result=viewModel.getProfile(homePageStudentSide.teacher_id)
            result.observe(viewLifecycleOwner
            ) {
                when (it) {
                    is Response.Success -> {
                        if (it.data!!.educator_quiz.size == 0) {
                            binding.NoQuizzes.visibility = View.VISIBLE
                        }
                        layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                        binding.RecyclerViewStudentSideQuiz.layoutManager = layoutManager
                        adapterGetQuiz =
                            RecyclerAdapterQuizTEachersSide(TeacherProfileRepoStudentSide.studentQuizWithNoZeroQuestions)

                        binding.RecyclerViewStudentSideQuiz.adapter = adapterGetQuiz
                        adapterGetQuiz.onClickListener(object :
                            RecyclerAdapterQuizTEachersSide.ClickListener {
                            override fun OnClick(position: Int) {
                                homePageStudentSide.quizTitle =
                                    homePageStudentSide.totalQuiz!!.get(position).title.toString()
                                homePageStudentSide.quizDescription =
                                    homePageStudentSide.totalQuiz!!.get(position).description.toString()
                                homePageStudentSide.quizLectureCount =
                                    homePageStudentSide.totalQuiz!!.get(position).questions.toString()
                                homePageStudentSide.quizid =
                                    homePageStudentSide.totalQuiz!!.get(position).id.toInt()
                                findNavController().navigate(R.id.quizShowPageStudentSide)
                            }
                        })
                    }
                }
            }

        }
            return binding.root

    }


}