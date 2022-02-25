package com.example.unacademy.Ui.StudentsSide

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentQuizResultPageBinding
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.QuizResultModelItem
import com.example.unacademy.viewmodel.viewmodelStudentside.GetQuizResultViewModel
import kotlinx.coroutines.launch
import org.eazegraph.lib.models.PieModel


class QuizResultPage : Fragment() {

    companion object
    {
        var quizresult=ArrayList<QuizResultModelItem>()
    }
    lateinit var binding:FragmentQuizResultPageBinding
    lateinit var getQuizResultViewModel: GetQuizResultViewModel
    var correctedQuestions:Float= 0F
    var totalScore:Float= 0F
    var score:Float= 0F
    var wrongQuestions:Float= 0F
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
        var progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Fetching Result ")
        progressDialog.setCancelable(false)
        progressDialog.show()
        binding.lifecycleOwner=this
        binding.quizResultViewModel=getQuizResultViewModel
        binding.ViewAnalysisStudentSide.setOnClickListener {
            findNavController().navigate(R.id.resultAnalysisPageStudentSide)
        }
        lifecycleScope.launch {
            var result=getQuizResultViewModel.QuizResult()
            result.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                        is Response.Success->
                        {
                            quizresult= it.data as ArrayList<QuizResultModelItem>
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
                            binding.pieChartCorrectedAnswer.addPieSlice(PieModel(
                                 "Corrected Questions",(correctedQuestions/it.data.size)*100.toFloat(), Color.parseColor("#4CAF50")
                            ))
                            binding.pieChartCorrectedAnswer.addPieSlice(
                                PieModel(
                                "UncorrectQuestions",100-(correctedQuestions/it.data.size)*100.toFloat(),Color.GRAY
                                )
                            )
                            binding.pieChartAttemptedQuestions.addPieSlice(
                                PieModel(
                                    "Attempted Questions",(correctedQuestions+wrongQuestions).toFloat(),Color.parseColor("#E79300"))
                            )
                            binding.WrongQuestionsPieChart.addPieSlice(
                                PieModel(
                                    "Wrong Questions",(wrongQuestions/it.data.size)*100,Color.RED
                                )
                            )
                            binding.WrongQuestionsPieChart.addPieSlice(
                                PieModel(
                                    "Un-Wrong Questions",100-(wrongQuestions/it.data.size)*100.toFloat(),Color.GRAY)
                                )

                            binding.pieChartAttemptedQuestions.startAnimation()
                            binding.pieChartCorrectedAnswer.startAnimation()
                            binding.WrongQuestionsPieChart.startAnimation()
                            binding.CorrectedQuestionsQuizResult.text=correctedQuestions.toString()
                            binding.WrongQuestionsQuizResult.text=wrongQuestions.toString()
                            binding.QuizResultScore.text=score.toString() + "/" + totalScore.toString()
                            progressDialog.dismiss()

                        }
                        is Response.Error-> Toast.makeText(context,it.errorMessage.toString(),Toast.LENGTH_LONG).show()
                    }
                })
        }
        return binding.root
    }


}