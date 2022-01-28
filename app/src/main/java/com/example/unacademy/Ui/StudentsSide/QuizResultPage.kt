package com.example.unacademy.Ui.StudentsSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentQuizResultPageBinding
import com.example.unacademy.viewmodel.viewmodelStudentside.GetQuizResultViewModel
import kotlinx.coroutines.launch


class QuizResultPage : Fragment() {

    lateinit var binding:FragmentQuizResultPageBinding
    lateinit var getQuizResultViewModel: GetQuizResultViewModel
    var correctedQuestions=0
    var totalScore=0
    var score=0
    var wrongQuestions=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    getQuizResultViewModel=ViewModelProvider(this)[GetQuizResultViewModel()::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_result_page, container, false)
        binding.lifecycleOwner=this
        binding.quizResultViewModel=getQuizResultViewModel
        lifecycleScope.launch {
            var result=getQuizResultViewModel.QuizResult()
            result.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                        is Response.Success->
                        {
                            binding.NoOfQuestionsResult.text= it.data!!.size.toString()
                            for(i in 0..(it.data.size-1))
                            {
                                totalScore+=it.data[i].question.marks
                                if(it.data[i].attempted_answer==it.data[i].correct_answer)
                                {
                                    correctedQuestions++
                                    score+=it.data[i].question.marks
                                }
                                else
                                {
                                    wrongQuestions++
                                }
                            }
                            binding.CorrectedQuestionsQuizResult.text=correctedQuestions.toString()
                            binding.WrongQuestionsQuizResult.text=wrongQuestions.toString()
                            binding.QuizResultScore.text=score.toString() + "/" + totalScore.toString()

                        }
                        is Response.Error-> Toast.makeText(context,it.errorMessage.toString(),Toast.LENGTH_LONG).show()
                    }
                })
        }
        return binding.root
    }


}