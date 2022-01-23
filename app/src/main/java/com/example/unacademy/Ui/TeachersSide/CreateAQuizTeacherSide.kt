package com.example.unacademy.Ui.TeachersSide

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
import com.example.unacademy.databinding.FragmentCreateAQuizTeacherSideBinding
import com.example.unacademy.viewmodel.CreateAQuizViewModel
import kotlinx.coroutines.launch


class CreateAQuizTeacherSide : Fragment(),View.OnClickListener {
    lateinit var binding:FragmentCreateAQuizTeacherSideBinding
    lateinit var createAQuizViewModel: CreateAQuizViewModel
    companion object
    {
        var Quizid:Int=0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
createAQuizViewModel=ViewModelProvider(this)[CreateAQuizViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_a_quiz_teacher_side, container, false)
       binding.lifecycleOwner=this
        binding.createAQuizViewModel=createAQuizViewModel

        binding.CreateAndAddQuestion.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.CreateAndAddQuestion-> {
                lifecycleScope.launch {
                    var result=createAQuizViewModel.CreateAQuiz()
                    result.observe(viewLifecycleOwner,
                        {
                            when(it)
                            {
                                is Response.Success->
                                {
                                    findNavController().navigate(R.id.action_createAQuizTeacherSide_to_addQuizQuestions)
                                    Quizid= it.data!!.id
                                }
                                is Response.Error->
                                {
                                    Toast.makeText(context,it.errorMessage.toString(),Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                }
            }
        }
    }


}