package com.example.unacademy.Ui.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentLogInBinding
//import com.example.unacademy.databinding.FragmentLogInBinding


class LogIn : Fragment() ,View.OnClickListener{
    private var binding:FragmentLogInBinding?=null
    private var validationFlagEmail = 0
    private var validationFlagPassword = 0
//     var logInViewModel= LogInViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = FragmentLogInBinding.inflate(inflater,container,false)
        emailFocusListener()
        passwordFocusListener()
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

        var passText= binding?.LoginPassword
        val navController = findNavController()
        var emailText= binding?.LoginEmailAdress
        when(v?.id)
        {

            R.id.LogInButton ->
            {
                emailText?.clearFocus()
                passText?.clearFocus()
                if(validationFlagEmail==1 && validationFlagPassword==1) {
                    navController.navigate(R.id.action_logIn_to_emailVerification)
                }

            }
            R.id.ForgotPassword-> navController.navigate(R.id.action_logIn_to_emailVerification)
            R.id.SignUpButtonLogIn->navController.navigate(R.id.action_logIn_to_signUp)
        }
    }
    private fun passwordFocusListener()
    {
        binding?.LoginPassword?.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                var passText= binding?.LoginPassword?.text.toString().trim()
                if(Validations.validPassword(passText) ==null){
                    binding!!.LoginPasswordContainer.helperText=""
                    validationFlagPassword=1
                }
                else {

                    binding!!.LoginPasswordContainer.helperText =
                        Validations.validPassword(passText).toString()
                    validationFlagPassword=0
                }
            }
        }
    }
    private fun emailFocusListener()

    {

        binding?.LoginEmailAdress?.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                var emailText= binding?.LoginEmailAdress?.text.toString().trim()
                if(Validations.emailValdiation(emailText) == null)
                {
                    binding!!.LoginEmailContainer.helperText=""
                    validationFlagEmail=1
                }
                else {
                    binding!!.LoginEmailContainer.helperText =
                        Validations.emailValdiation(emailText)
                    validationFlagEmail=0
                }
            }
        }
    }

}

