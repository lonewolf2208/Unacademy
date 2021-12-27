package com.example.unacademy.Ui.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Repository.OtpRepo
import com.example.unacademy.Repository.SignUpRepo
import com.example.unacademy.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentOtpBinding
import kotlinx.coroutines.launch


class Otp : Fragment(),View.OnClickListener {

    private var _binding : FragmentOtpBinding?=null
    private val binding
    get() =_binding!!
    private  var otp:OtpRepo?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentOtpBinding.inflate(inflater, container, false)
        otp= OtpRepo(RetrofitClient.init())
        binding.otpVerifyButton.setOnClickListener(this)
        binding.ResendOtp.setOnClickListener(this)
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
            R.id.otpVerifyButton-> {
                binding.otpVerifyButton.isEnabled=false
                var email:String=SignUp.email

//                lifecycleScope.launch {
//                    email= Splash_Screen.readInfo("email").toString()
//                }
                otp?.OtpApi(email,binding.editTextTextPersonName4.text.toString())
                otp?.OtpResponse?.observe(this@Otp,{
                    when (it) {
                        is com.example.unacademy.Repository.Response.Success -> {
                            binding.progressBarOtp.visibility=View.INVISIBLE
                            Toast.makeText(context,"Otp Verified", Toast.LENGTH_LONG).show()
                            navController.navigate(R.id.createPassword)
                        }
                        is com.example.unacademy.Repository.Response.Error -> {
                            binding.progressBarOtp.visibility=View.INVISIBLE
                            binding.otpVerifyButton.isEnabled=true
                            Toast.makeText(context,it.errorMessage.toString(), Toast.LENGTH_LONG).show()
                        }
                        is com.example.unacademy.Repository.Response.Loading->
                        {
                            binding.progressBarOtp.visibility=View.VISIBLE
                        }
                    }
                })

            }
            R.id.ResendOtp->
            { var SignUpRepo=SignUpRepo(RetrofitClient.init())
                SignUpRepo?.SignUpApi(SignUp.email,SignUp.name)
                Toast.makeText(context,"Otp has been Sent Successfully",Toast.LENGTH_LONG).show()
            }
        }
    }

}