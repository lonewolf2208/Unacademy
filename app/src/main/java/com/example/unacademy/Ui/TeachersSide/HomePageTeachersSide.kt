package com.example.unacademy.Ui.TeachersSide

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentHomePageTeachersSideBinding
import com.example.unacademy.viewModel.HomePageViewModel

class HomePageTeachersSide : Fragment() {

    lateinit var binding :FragmentHomePageTeachersSideBinding
    lateinit var homePageViewModel:HomePageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageViewModel=ViewModelProvider(this)[HomePageViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_page_teachers_side,
            container,
            false
        )
        binding.lifecycleOwner=this
        binding.homePageViewModel=homePageViewModel
        return binding.root
    }
}