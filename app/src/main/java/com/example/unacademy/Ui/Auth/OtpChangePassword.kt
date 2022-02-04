package com.example.unacademy.Ui.Auth

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Repository.AuthRepo.OtpRepo
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.AuthRepo.SignUpRepo
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentOtpChangePasswordBinding


class OtpChangePassword : Fragment(),View.OnClickListener {

    private var _binding : FragmentOtpChangePasswordBinding?=null
    private val binding
        get() =_binding!!
    private  var otp: OtpRepo?=null
    lateinit var timerCountDownTimer:CountDownTimer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentOtpChangePasswordBinding.inflate(inflater, container, false)
        startTimer()
        binding.otpVerifyButtonChangePassword.setOnClickListener(this)
        binding.ResendOtpChangePassword.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        val navController=findNavController()
        when(v?.id)
        {
            R.id.otpVerifyButtonChangePassword-> {
                otp= OtpRepo(RetrofitClient.init())
                binding.otpVerifyButtonChangePassword.isEnabled=false
                var email:String=EmailVerification.emailChangePassword

//                lifecycleScope.launch {
//                    email= Splash_Screen.readInfo("email").toString()
//                }
                otp?.OtpApi(email,binding.editTextChangePassword.text.toString())
                otp?.OtpResponse?.observe(this@OtpChangePassword,{
                    when (it) {
                        is Response.Success -> {
                            binding.progressBarOtpChangePassword.visibility=View.INVISIBLE
                            Toast.makeText(context,"Otp Verified", Toast.LENGTH_LONG).show()
                            navController.navigate(R.id.action_otpChangePassword_to_changePassword)
                        }
                        is Response.Error -> {
                            binding.progressBarOtpChangePassword.visibility=View.INVISIBLE
                            binding.otpVerifyButtonChangePassword.isEnabled=true
                            Toast.makeText(context,it.errorMessage.toString(), Toast.LENGTH_LONG).show()
                        }
                        is Response.Loading->
                        {
                            binding.progressBarOtpChangePassword.visibility=View.VISIBLE
                            binding.otpVerifyButtonChangePassword.isEnabled=false
//                            Toast.makeText(context,"Loading", Toast.LENGTH_LONG).show()
                        }

                    }
                })

            }
            R.id.ResendOtpChangePassword->
            {
                startTimer()
                var SignUpRepo= SignUpRepo(RetrofitClient.init())
                SignUpRepo?.SignUpApi(SignUp.email,SignUp.name)
                Toast.makeText(context,"Otp has been Sent Successfully", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun startTimer() {
        timerCountDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                if (timeLeft.toString().length == 2)
                    binding.TImerChangePassword.text = "00:" + timeLeft.toString()
                else
                    binding.TImerChangePassword.text = "00:0" + timeLeft.toString()
                binding.ResendOtpChangePassword.isClickable=false
                binding.ResendOtpChangePassword.setTextColor(Color.DKGRAY)
            }

            override fun onFinish() {
                binding.TImerChangePassword.text = "00:00"
                binding.ResendOtpChangePassword.isClickable=true
                binding.ResendOtpChangePassword.setTextColor(Color.parseColor("#C2003356"))
            }

        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timerCountDownTimer.cancel()
    }
}


