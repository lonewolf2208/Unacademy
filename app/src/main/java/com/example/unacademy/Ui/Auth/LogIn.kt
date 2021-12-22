package com.example.unacademy.Ui.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentLogInBinding
//import com.example.unacademy.databinding.FragmentLogInBinding
import com.example.unacademy.viewModel.LogInViewModel


class LogIn : Fragment() ,View.OnClickListener{
    private var binding:FragmentLogInBinding?=null

     var logInViewModel= LogInViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = FragmentLogInBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
//        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_log_in,container,false)
//
//        binding.lifecycleOwner = this
//        binding.logInViewModel=LogInViewModel()
//
//        binding.ForgotPassword.setOnClickListener {
//            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.emailVerification) }
//        }
//        logInViewModel.loginEmail.observe(viewLifecycleOwner,
//            {
//
//            })
//        binding.LogInButton.setOnClickListener{
//            Toast.makeText(context,logInViewModel.loginEmail.value.toString(),Toast.LENGTH_LONG).show()
//            if(logInViewModel.Validations()==true)
//            {
//                view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.signUp) }
//            }
//            else{
//                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
//            }
//        }
        binding!!.SignUpButtonLogIn.setOnClickListener(this)
//        binding.lifecycleOwner = this
//        binding.logInViewModel=LogInViewModel()
        binding!!.LogInButton.setOnClickListener(this)
        binding!!.ForgotPassword.setOnClickListener(this)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.root
    }

    override fun onClick(v: View?) {
        val navController = findNavController()
        when(v?.id)
        {
            R.id.ForgotPassword ->navController.navigate(R.id.action_logIn_to_emailVerification)
            R.id.LogInButton -> navController.navigate(R.id.signUp)
            R.id.SignUpButtonLogIn->navController.navigate(R.id.action_logIn_to_signUp)
        }
    }

}