package com.example.unacademy.Ui.StudentsSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.databinding.FragmentQuizShowPageStudentSideBinding
import com.example.unacademy.viewmodel.viewmodelStudentside.GetQuizQuestionsViewModel


class QuizShowPageStudentSide : Fragment() {

lateinit var binding:FragmentQuizShowPageStudentSideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_show_page_student_side, container, false)
        binding.QUestionCountHomePageStudentSide.text=homePageStudentSide.quizLectureCount
        binding.QuizDescriptionHomePage.text=homePageStudentSide.quizDescription
        binding.QuizTitleHomePage.text=homePageStudentSide.quizTitle
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_quizShowPageStudentSide_to_questionPageStudentSide)
        }
        return binding.root
    }


}