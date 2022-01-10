package com.example.unacademy.Ui.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.AuthRepo.SignUpRepo
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentSignUpBinding


class SignUp : Fragment(),View.OnClickListener {


    companion object
    {
        var email=""
        var name=""
    }

    private var _binding:FragmentSignUpBinding? = null
    private val binding
    get() = _binding
    private var validationFlagEmail = 0
    private var nameValidation=0
    private var SignupRepo: SignUpRepo?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        binding?.LogInSignUp?.setOnClickListener(this)
        binding?.verifyEmailSignUp?.setOnClickListener(this)
//        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up,container,false)
//        binding.LogInSignUp.setOnClickListener {
//            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.logIn) }
//        }
        emailFocusListener()
        nameFocusListener()
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onClick(v: View?) {
        var navController = findNavController()
        when(v?.id)
        {
            R.id.LogInSignUp -> navController.navigate(R.id.action_signUp_to_logIn)
            R.id.verifyEmailSignUp-> {
                SignupRepo= SignUpRepo(RetrofitClient.init())
                binding?.SignUpEmailAdress?.clearFocus()
                binding?.SignUpName?.clearFocus()
                if(validationFlagEmail==1 && nameValidation == 1) {
                    binding?.verifyEmailSignUp?.isEnabled=false

                    SignupRepo?.SignUpApi(binding?.SignUpEmailAdress?.text.toString(),
                        binding?.SignUpName?.text.toString())
                    SignupRepo?.SignUpResponse?.observe(this@SignUp,{
                        when (it) {
                            is Response.Success -> {
                                binding?.progressBarSignUp?.visibility=View.INVISIBLE
                                name=binding?.SignUpName?.text.toString()
                                email= binding?.SignUpEmailAdress?.text.toString()
                                Toast.makeText(context,"Otp Has Been Sent to your email", Toast.LENGTH_LONG).show()
                                navController.navigate(R.id.action_signUp_to_otp)
                            }
                            is Response.Error -> {
                                binding?.progressBarSignUp?.visibility=View.INVISIBLE
                                binding?.verifyEmailSignUp?.isEnabled=true
                                Toast.makeText(context,it.errorMessage.toString(), Toast.LENGTH_LONG).show()
                            }
                            is Response.Loading->
                            {
                                binding?.progressBarSignUp?.visibility=View.VISIBLE
                            }
                            else-> {
                                Toast.makeText(context,"ElseBlock", Toast.LENGTH_LONG).show()
                            }
                        }
                    })

                }
            }
        }
    }

    private fun emailFocusListener()

    {

        binding?.SignUpEmailAdress?.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                var emailText= binding?.SignUpEmailAdress?.text.toString().trim()
                if(Validations.emailValdiation(emailText) == null)
                {
                    binding!!.SignUpEmailAdressContainer.helperText=""
                    validationFlagEmail=1
                }
                else {
                    binding!!.SignUpEmailAdressContainer.helperText =
                        Validations.emailValdiation(emailText)
                    validationFlagEmail=0
                }
            }
        }
    }

    private fun nameFocusListener()
    {
        binding?.SignUpName?.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus) {
                var name = binding!!.SignUpName.text.toString()
                if(Validations.NameValidations(name)==null) {
                    binding!!.signUpNameContainer.helperText=""
                    nameValidation=1
                } else {
                    binding!!.signUpNameContainer.helperText="Required"
                    nameValidation=0
                }
            }
        }
    }
}