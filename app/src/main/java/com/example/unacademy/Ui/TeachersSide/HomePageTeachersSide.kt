package com.example.unacademy.Ui.TeachersSide

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unacademy.databinding.FragmentHomePageTeachersSideBinding

class HomePageTeachersSide : Fragment() {

    private var _binding :FragmentHomePageTeachersSideBinding?=null
    private val binding
        get() =_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentHomePageTeachersSideBinding.inflate(inflater,container,false)

        return binding.root
    }


}