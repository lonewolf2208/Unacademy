package com.example.unacademy.Ui.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Repository.SignUpRepo
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentEmailVerificationBinding
import com.example.unacademy.databinding.FragmentLogInBinding
import com.example.unacademy.viewModel.EmailVerificationViewModel
import com.example.unacademy.viewModel.LogInViewModel

class EmailVerification : Fragment(),View.OnClickListener {
    companion object
    {
        var emailChangePassword=""
    }
    private var _binding: FragmentEmailVerificationBinding?=null
    private val binding
    get()=_binding!!
    private var validationFlagEmail=0
    private var SignUpRepo:SignUpRepo?=null
//    lateinit var emailVerificationViewModel: EmailVerificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentEmailVerificationBinding.inflate(inflater,container,false)
        SignUpRepo=SignUpRepo(RetrofitClient.init())

//        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_email_verification,container,false)
//        binding.lifecycleOwner=this
//        binding.emailVerificationViewModel= EmailVerificationViewModel()
//        binding.EmailVerify.setOnClickListener {
//            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.changePassword) }
//        }
        emailFocusListener()
        binding.EmailVerifyButtonEmailVerification.setOnClickListener(this)
        return binding.root

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_email_verification, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onClick(v: View?) {
        val navController=findNavController()
        when(v?.id)
        {
            R.id.EmailVerifyButtonEmailVerification-> {
                binding.emailEmailVerification.clearFocus()
                if(validationFlagEmail==1) {
                    emailChangePassword=binding?.emailEmailVerification?.text.toString()
                    SignUpRepo?.SignUpApi( binding?.emailEmailVerification?.text.toString(),"")
                    SignUpRepo?.SignUpResponse?.observe(this@EmailVerification,{
                        when (it) {
                            is com.example.unacademy.Repository.Response.Success -> {
//                                binding?.progressBarSignUp?.visibility=View.INVISIBLE
//                                SignUp.name =binding?.SignUpName?.text.toString()
//                                SignUp.email = binding?.SignUpEmailAdress?.text.toString()
//                                lifecycleScope.launch {
//                                    Splash_Screen.saveInfo("name", binding?.SignUpName?.text.toString())
//                                    Toast.makeText(context,binding?.SignUpEmailAdress?.text.toString(),Toast.LENGTH_LONG).show()
//                                    Splash_Screen.saveInfo("email", binding?.SignUpEmailAdress?.text.toString())
//                                }

                                Toast.makeText(context,"Otp Has Been Sent to your email", Toast.LENGTH_LONG).show()
                                navController.navigate(R.id.otpChangePassword)
                            }
                            is com.example.unacademy.Repository.Response.Error -> {
//                                binding?.progressBarSignUp?.visibility=View.INVISIBLE
//                                binding?.verifyEmailSignUp?.isEnabled=true
                                Toast.makeText(context,it.errorMessage.toString(), Toast.LENGTH_LONG).show()
                            }
                            is com.example.unacademy.Repository.Response.Loading->
                            {
//                                binding?.progressBarSignUp?.visibility=View.VISIBLE
                                Toast.makeText(context,it.errorMessage.toString(), Toast.LENGTH_LONG).show()
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

        binding?.emailEmailVerification?.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                var emailText= binding?.emailEmailVerification?.text.toString().trim()
                if(Validations.emailValdiation(emailText) == null)
                {
                    binding!!.emailEmailVerificationContainer.helperText=""
                    validationFlagEmail=1
                }
                else {
                    binding!!.emailEmailVerificationContainer.helperText =
                        Validations.emailValdiation(emailText)
                    validationFlagEmail=0
                }
            }
        }
    }

}