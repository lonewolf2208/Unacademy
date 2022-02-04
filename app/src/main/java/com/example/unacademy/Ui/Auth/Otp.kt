package com.example.unacademy.Ui.Auth

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Repository.AuthRepo.OtpRepo
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.AuthRepo.SignUpRepo
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentOtpBinding


class Otp : Fragment(),View.OnClickListener {

    private var _binding: FragmentOtpBinding? = null
    private val binding
        get() = _binding!!
    private var otp: OtpRepo? = null
    lateinit var timerCountDownTimer: CountDownTimer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        startTimer()
        binding.otpVerifyButton.setOnClickListener(this)
        binding.ResendOtp.setOnClickListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        timerCountDownTimer.cancel()
    }

    override fun onClick(v: View?) {
        val navController = findNavController()
        when (v?.id) {
            R.id.otpVerifyButton -> {
                otp = OtpRepo(RetrofitClient.init())
                binding.otpVerifyButton.isEnabled = false
                var email: String = SignUp.email

//                lifecycleScope.launch {
//                    email= Splash_Screen.readInfo("email").toString()
//                }
                otp?.OtpApi(email, binding.editTextTextPersonName4.text.toString())
                otp?.OtpResponse?.observe(this@Otp, {
                    when (it) {
                        is Response.Success -> {
                            binding.progressBarOtp.visibility = View.INVISIBLE
                            Toast.makeText(context, "Otp Verified", Toast.LENGTH_LONG).show()
                            navController.navigate(R.id.action_otp_to_createPassword)
                        }
                        is Response.Error -> {
                            binding.progressBarOtp.visibility = View.INVISIBLE
                            binding.otpVerifyButton.isEnabled = true
                            Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_LONG)
                                .show()
                        }
                        is Response.Loading -> {
                            binding.progressBarOtp.visibility = View.VISIBLE
                        }
                    }
                })

            }
            R.id.ResendOtp -> {
                startTimer()
                var SignUpRepo = SignUpRepo(RetrofitClient.init())
                SignUpRepo?.SignUpApi(SignUp.email, SignUp.name)
                Toast.makeText(context, "Otp has been Sent Successfully", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun startTimer() {
        timerCountDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                if (timeLeft.toString().length == 2)
                    binding.TImerOtp.text = "00:" + timeLeft.toString()
                else
                    binding.TImerOtp.text = "00:0" + timeLeft.toString()
                binding.ResendOtp.isClickable = false
                binding.ResendOtp.setTextColor(Color.DKGRAY)
            }

            override fun onFinish() {
                binding.TImerOtp.text = "00:00"
                binding.ResendOtp.isClickable = true
                binding.ResendOtp.setTextColor(Color.parseColor("#C2003356"))
            }

        }.start()
    }

}