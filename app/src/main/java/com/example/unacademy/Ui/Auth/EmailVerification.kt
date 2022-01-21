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
import com.example.unacademy.databinding.FragmentEmailVerificationBinding

class EmailVerification : Fragment(),View.OnClickListener {
    companion object
    {
        var emailChangePassword=""
    }
    private var _binding: FragmentEmailVerificationBinding?=null
    private val binding
    get()=_binding!!
    private var validationFlagEmail=0
    private var SignUpRepo: SignUpRepo?=null
//    lateinit var emailVerificationViewModel: EmailVerificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentEmailVerificationBinding.inflate(inflater,container,false)
        emailFocusListener()
        binding.EmailVerifyButtonEmailVerification.setOnClickListener(this)
        return binding.root

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
                    SignUpRepo= SignUpRepo(RetrofitClient.init())
                    emailChangePassword=binding?.emailEmailVerification?.text.toString()
                    SignUpRepo?.SignUpApi( binding?.emailEmailVerification?.text.toString(),"")
                    SignUpRepo?.SignUpResponse?.observe(this@EmailVerification,{
                        when (it) {
                            is Response.Success -> {
                                binding?.progressBarEmailVerification?.visibility=View.INVISIBLE
//
                                Toast.makeText(context,"Otp Has Been Sent to your email", Toast.LENGTH_LONG).show()
                                navController.navigate(R.id.action_emailVerification_to_otpChangePassword)
                            }
                            is Response.Error -> {
                                binding?.EmailVerifyButtonEmailVerification?.isEnabled=true
                                binding?.progressBarEmailVerification?.visibility=View.INVISIBLE
//                                binding?.progressBarSignUp?.visibility=View.INVISIBLE
//                                binding?.verifyEmailSignUp?.isEnabled=true
                                Toast.makeText(context,it.errorMessage.toString(), Toast.LENGTH_LONG).show()
                            }
                            is Response.Loading->
                            {
                                binding?.EmailVerifyButtonEmailVerification?.isEnabled=false
                                binding?.progressBarEmailVerification?.visibility=View.VISIBLE

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