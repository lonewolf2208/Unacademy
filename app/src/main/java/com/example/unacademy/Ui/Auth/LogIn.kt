package com.example.unacademy.Ui.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentLogInBinding
import com.example.unacademy.viewModel.LogInViewModel


class LogIn : Fragment() {
    lateinit var binding: FragmentLogInBinding
     lateinit var logInViewModel: LogInViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_log_in,container,false)

        binding.lifecycleOwner = this
        binding.logInViewModel=LogInViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logInViewModel=ViewModelProvider(this)[LogInViewModel::class.java]
    }

}