package com.example.unacademy.Ui.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentChooseRoleBinding


class ChooseRole : Fragment() {


  private var _binding:FragmentChooseRoleBinding?=null
    private val binding
        get()=_binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentChooseRoleBinding.inflate(inflater,container,false)
        binding?.checkBoxTeacher?.setOnClickListener {
            binding!!.checkBoxTeacher.isChecked=false
            findNavController().navigate(R.id.action_chooseRole_to_teachers_profile)
        }
        binding?.checkBoxStudent?.setOnClickListener {
            binding!!.checkBoxStudent.isChecked=false
            findNavController().navigate(R.id.action_chooseRole_to_studentInfo)
        }
        return binding?.root
    }


}