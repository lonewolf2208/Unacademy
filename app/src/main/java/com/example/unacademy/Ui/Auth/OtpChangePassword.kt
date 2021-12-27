package com.example.unacademy.Ui.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Repository.OtpRepo
import com.example.unacademy.Repository.SignUpRepo
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentOtpBinding
import com.example.unacademy.databinding.FragmentOtpChangePasswordBinding


class OtpChangePassword : Fragment(),View.OnClickListener {

    private var _binding : FragmentOtpChangePasswordBinding?=null
    private val binding
        get() =_binding!!
    private  var otp: OtpRepo?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentOtpChangePasswordBinding.inflate(inflater, container, false)
        otp= OtpRepo(RetrofitClient.init())
        binding.otpVerifyButtonChangePassword.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        val navController=findNavController()
        when(v?.id)
        {
            R.id.otpVerifyButtonChangePassword-> {
                binding.otpVerifyButtonChangePassword.isEnabled=false
                var email:String=EmailVerification.emailChangePassword

//                lifecycleScope.launch {
//                    email= Splash_Screen.readInfo("email").toString()
//                }
                otp?.OtpApi(email,binding.editTextChangePassword.text.toString())
                otp?.OtpResponse?.observe(this@OtpChangePassword,{
                    when (it) {
                        is com.example.unacademy.Repository.Response.Success -> {
                            binding.progressBarOtpChangePassword.visibility=View.INVISIBLE
                            Toast.makeText(context,"Otp Verified", Toast.LENGTH_LONG).show()
                            navController.navigate(R.id.changePassword)
                        }
                        is com.example.unacademy.Repository.Response.Error -> {
                            binding.progressBarOtpChangePassword.visibility=View.INVISIBLE
                            binding.otpVerifyButtonChangePassword.isEnabled=true
                            Toast.makeText(context,it.errorMessage.toString(), Toast.LENGTH_LONG).show()
                        }
                        is com.example.unacademy.Repository.Response.Loading->
                        {
                            binding.progressBarOtpChangePassword.visibility=View.VISIBLE
//                            Toast.makeText(context,"Loading", Toast.LENGTH_LONG).show()
                        }

                    }
                })

            }
            R.id.ResendOtp->
            { var SignUpRepo= SignUpRepo(RetrofitClient.init())
                SignUpRepo?.SignUpApi(SignUp.email,SignUp.name)
                Toast.makeText(context,"Otp has been Sent Successfully", Toast.LENGTH_LONG).show()
            }
        }
    }
    }


