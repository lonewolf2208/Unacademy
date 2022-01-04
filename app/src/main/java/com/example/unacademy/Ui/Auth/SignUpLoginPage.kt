package com.example.unacademy.Ui.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentSignUpLoginPageBinding


class SignUpLoginPage : Fragment(),View.OnClickListener {

    private var binding :FragmentSignUpLoginPageBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding= FragmentSignUpLoginPageBinding.inflate(inflater,container,false)
        binding?.OpeningLoginButton?.setOnClickListener(this)
        binding?.OpeningSignUpButton?.setOnClickListener(this)
        return binding!!.root
    }

    override fun onClick(v: View?) {
        var navController=findNavController()
        when(v?.id)
        {
            R.id.OpeningLoginButton->navController.navigate(R.id.action_signUpLoginPage_to_logIn)
            R.id.OpeningSignUpButton->navController.navigate(R.id.action_signUpLoginPage_to_signUp)
        }
    }


}