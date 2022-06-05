package com.example.unacademy.Ui.TeachersSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.Adapter.RecyclerAdapterTeachersSideHomePage
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterQuestions
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterQuizTEachersSide
import com.example.unacademy.R
import com.example.unacademy.Repository.TeachersSideRepo.GetQUestions
import com.example.unacademy.Repository.TeachersSideRepo.createSeriesRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentQuizShowTEachersBinding
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.Question
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuizShowTEachers : Fragment() {
    lateinit var binding:FragmentQuizShowTEachersBinding
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: RecyclerAdapterQuestions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding =DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_show_t_eachers, container, false)
        var token:String=""
        val job =lifecycleScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
        GetQUestions(RetrofitClient.init()).getQuestionsApi(HomePageTeachersSide.quizid,token).observe(viewLifecycleOwner
        ) {
            when (it) {
                is com.example.unacademy.Repository.Response.Success -> {
                    layoutManager = LinearLayoutManager(
                        container?.context,
                        LinearLayoutManager.VERTICAL, false
                    )
                    binding.REcyclerViewQuestion.layoutManager = layoutManager
                    adapter = RecyclerAdapterQuestions(it.data)
                    binding.REcyclerViewQuestion.adapter = adapter
                }
            }
        }

        return binding.root
    }


}