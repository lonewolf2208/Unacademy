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
import com.example.unacademy.databinding.FragmentQuizShowPageStudentSideBinding


class QuizShowPageStudentSide : Fragment() {

lateinit var binding:FragmentQuizShowPageStudentSideBinding
    lateinit var adapterGetQuiz:RecyclerAdapterQuizTEachersSide
    private var layoutManager: RecyclerView.LayoutManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_quiz_show_page_student_side,
            container,
            false
        )
        binding.QUestionCountHomePageStudentSide.text = homePageStudentSide.quizLectureCount
        binding.QuizDescriptionHomePage.text = homePageStudentSide.quizDescription
        binding.QuizTitleHomePage.text=homePageStudentSide.quizTitle
        binding.textView97.text=homePageStudentSide.quizDuration.toString() + " Mins"
        layoutManager= StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.RecyclerAdapterMoreQuizes.layoutManager=layoutManager
        adapterGetQuiz= RecyclerAdapterQuizTEachersSide(homePageStudentSide.totalQuiz)
        binding.RecyclerAdapterMoreQuizes.adapter=adapterGetQuiz
        adapterGetQuiz.onClickListener(object : RecyclerAdapterQuizTEachersSide.ClickListener {
            override fun OnClick(position: Int) {
                homePageStudentSide.quizTitle = homePageStudentSide.totalQuiz!!.get(position).title.toString()
                homePageStudentSide.quizDescription =homePageStudentSide.totalQuiz!!.get(position).description.toString()
                homePageStudentSide.quizLectureCount =homePageStudentSide.totalQuiz!!.get(position).questions.toString()
                homePageStudentSide.quizid =homePageStudentSide.totalQuiz!!.get(position).id.toInt()
                homePageStudentSide.quizDuration= homePageStudentSide.totalQuiz!![position].duration
                findNavController().navigate(R.id.quizShowPageStudentSide)
            }
        })
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_quizShowPageStudentSide_to_questionPageStudentSide)
        }
        return binding.root
    }


}