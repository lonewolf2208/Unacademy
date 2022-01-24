package com.example.unacademy.Ui.StudentsSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentQuestionPageStudentSideBinding
import com.example.unacademy.models.QuizQuestionsModel.quizQuestionsModel
import com.example.unacademy.viewmodel.viewmodelStudentside.GetQuizQuestionsViewModel
import kotlinx.coroutines.launch


class QuestionPageStudentSide : Fragment() {

    lateinit var binding:FragmentQuestionPageStudentSideBinding
    lateinit var getQuizQuestionsViewModel:GetQuizQuestionsViewModel
    var i=0
    var ans =0
     var questionsQuiz: List<quizQuestionsModel>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getQuizQuestionsViewModel= ViewModelProvider(this)[GetQuizQuestionsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_question_page_student_side, container, false)
        binding.lifecycleOwner=this
        binding.getQuizQuestionViewModel=getQuizQuestionsViewModel
        binding.Option1QuizPage.setOnClickListener {
            ans=1
        }
        binding.Option2QuizPage.setOnClickListener {
            ans=2
        }
        binding.Option3QuizPage.setOnClickListener {
            ans=3
        }
        binding.Option4QuizPage.setOnClickListener {
            ans=4
        }
        lifecycleScope.launch {
            var result=getQuizQuestionsViewModel.getQuestions()
            result.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                        is Response.Success->
                        {
                            Toast.makeText(context,"Success",Toast.LENGTH_LONG)
                          questionsQuiz=it.data
                            binding.QuestionsQuizPage.text= questionsQuiz?.get(i)!!.question
                            binding.Option1QuizPage.text=questionsQuiz?.get(i)!!.option1
                            binding.Option2QuizPage.text=questionsQuiz?.get(i)!!.option2
                            binding.Option3QuizPage.text=questionsQuiz?.get(i)!!.option3
                            binding.Option4QuizPage.text=questionsQuiz?.get(i)!!.option4

                        }
                        is Response.Error->Toast.makeText(context,it.errorMessage.toString(),Toast.LENGTH_LONG).show()
                    }
                })
        }

        binding.NextQuestionQuizPAge.setOnClickListener {
            i++
            if (i < questionsQuiz!!.size) {
                if(i== (questionsQuiz!!.size-1))
                {
                 binding.NextQuestionQuizPAge.text="Complete Quiz"
                }

                if(ans==0)
                {
                    Toast.makeText(context,"Please select One of the otions to continue further.",Toast.LENGTH_LONG).show()}
        lifecycleScope.launch {
          getQuizQuestionsViewModel.UploadQuestion(questionsQuiz?.get(i-1)!!.id, ans)
//            result.observe(
//                viewLifecycleOwner,
//                {
//                    when(it)
//                    {
//                        is Response.Success->Toast.makeText(context,"Succc",Toast.LENGTH_LONG).show()
//                    }
//                }
//            )
        }
                binding.QuestionsQuizPage.text = questionsQuiz?.get(i)!!.question
                binding.Option1QuizPage.text = questionsQuiz?.get(i)!!.option1
                binding.Option2QuizPage.text = questionsQuiz?.get(i)!!.option2
                binding.Option3QuizPage.text = questionsQuiz?.get(i)!!.option3
                binding.Option4QuizPage.text = questionsQuiz?.get(i)!!.option4
            }
            if(i== (questionsQuiz!!.size))
            {

                findNavController().navigate(R.id.action_questionPageStudentSide_to_homePageStudentSide)
            }
        }
            return binding.root
    }


}
