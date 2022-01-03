package com.example.unacademy.Ui.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return binding?.root
    }


}