package com.example.unacademy.Ui.TeachersSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentAddQuizQuestionsBinding
import com.example.unacademy.viewmodel.AddQuizQuestionViewModel
import kotlinx.coroutines.launch
import android.os.Build
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController


class AddQuizQuestions : Fragment(),View.OnClickListener{

    lateinit var binding:FragmentAddQuizQuestionsBinding
    lateinit var addQuizQuestionViewModel: AddQuizQuestionViewModel
    var options=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addQuizQuestionViewModel=ViewModelProvider(this)[AddQuizQuestionViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_quiz_questions, container, false)
        binding.lifecycleOwner=this
        binding.FinishQuiz.setOnClickListener(this)
        binding.addQuizQuestionsViewModel=addQuizQuestionViewModel
        binding.AddNextQuestion.setOnClickListener(this)
        binding.spinner2.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)
                addQuizQuestionViewModel.answer.postValue(item.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
        binding.AddOptions.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.AddNextQuestion -> {
                lifecycleScope.launch {
                    if (addQuizQuestionViewModel.Validations() == null) {
                        var result = addQuizQuestionViewModel.UploadQuizQuestion()
                        result.observe(viewLifecycleOwner,
                            {
                                when (it) {
                                    is Response.Success -> {
                                        Toast.makeText(
                                            context,
                                            "Question Created",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        binding.QuizQuestion.setText("")
                                        binding.Option1.setText("")
                                        binding.Option2.setText("")
                                        binding.Option3.setText("")
                                        binding.Option4.setText("")
                                        binding.spinner2.isSelected = false

                                    }
                                    is Response.Error -> {
                                        Toast.makeText(
                                            context,
                                            it.errorMessage.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            })
                    }
                }
            }
                R.id.FinishQuiz->
                {
                    Toast.makeText(context,"Quiz has been created",Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_addQuizQuestions_to_homePageTeachersSide)
                }
                R.id.AddOptions->
                {
                    if(options==1)
                    {
                        binding.Option3.visibility=View.VISIBLE
                        binding.Option3.text=binding.AddOptionsText.text
                        binding.textView78.visibility=View.VISIBLE
                        options++
                    }
                    else if(options==2)
                    {
                        binding.Option4.visibility=View.VISIBLE
                        binding.Option4.text=binding.AddOptionsText.text
                        binding.textView79.visibility=View.VISIBLE
                        options++
                    }
                     else
                    {
                        Toast.makeText(context,"Not more than 4 Options can be added ",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
