package com.example.unacademy.Ui.Auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unacademy.Activities.NavBarActivity
import com.example.unacademy.Activities.StudentSideActivity
import com.example.unacademy.R
import com.example.unacademy.Repository.AuthRepo.ApiRepo
import com.example.unacademy.Repository.AuthRepo.GetTokenRepo
//import com.example.unacademy.Repository.AuthRepo.ApiRepo
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentLogInBinding
import kotlinx.coroutines.launch


class LogIn : Fragment() ,View.OnClickListener{
    private var binding:FragmentLogInBinding?=null
    private var validationFlagEmail = 0
    lateinit var ApiRepo: ApiRepo
//     var logInViewModel= LogInViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = FragmentLogInBinding.inflate(inflater,container,false)
        emailFocusListener()
        binding!!.SignUpButtonLogIn.setOnClickListener(this)
        binding!!.LogInButton.setOnClickListener(this)
        binding!!.ForgotPassword.setOnClickListener(this)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.root
    }

    override fun onClick(v: View?) {

        var passText = binding?.LoginPassword
        val navController = findNavController()
        var emailText = binding?.LoginEmailAdress
        when (v?.id) {

            R.id.LogInButton -> {
                emailText?.clearFocus()
                passText?.clearFocus()
                if (validationFlagEmail == 1 )
                {
                    ApiRepo= ApiRepo(RetrofitClient.init())
                    binding?.LogInButton?.isEnabled = false
                        ApiRepo?.getLoginApi(emailText?.text.toString(),passText?.text.toString())
                    ApiRepo?.ApiResponse.observe(this@LogIn) {
                        when (it) {
                            is Response.Success -> {
                                binding?.LogInButton?.isEnabled = true
                                var teacher = it.data?.is_educator
                                var student = it.data?.is_student
                                binding?.progressBarLogin?.visibility = View.INVISIBLE
                                var GetTokenRepo = GetTokenRepo(RetrofitClient.init())
                                GetTokenRepo.getToken(
                                    emailText?.text.toString(),
                                    passText?.text.toString()
                                )
                                GetTokenRepo.TokenResponse.observe(this@LogIn
                                ) {
                                    when (it) {
                                        is Response.Success -> {
                                            lifecycleScope.launch {
                                                Splash_Screen.saveInfo(
                                                    "access",
                                                    it.data?.access.toString()
                                                )
                                                Splash_Screen.saveInfo(
                                                    "refresh",
                                                    it.data?.refresh.toString()
                                                )
                                            }
                                            if (teacher == true) {
                                                val intent = Intent(
                                                    activity,
                                                    NavBarActivity::class.java
                                                )
                                                lifecycleScope.launch {
                                                    Splash_Screen.save(
                                                        "teacherloggedIn",
                                                        true
                                                    )
                                                }
                                                startActivity(intent)
                                            } else if (student == true) {
                                                val intent = Intent(
                                                    activity,
                                                    StudentSideActivity::class.java
                                                )
                                                lifecycleScope.launch {
                                                    Splash_Screen.save(
                                                        "studentloggedIn",
                                                        true
                                                    )
                                                }
                                                startActivity(intent)
                                            } else {
                                                findNavController().navigate(R.id.action_logIn_to_chooseRole)
                                            }
                                        }
                                        is Response.Error -> Toast.makeText(
                                            context,
                                            it.errorMessage.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                            }
                            is Response.Error -> {
                                binding?.LogInButton?.isEnabled = true
                                binding?.progressBarLogin?.visibility = View.INVISIBLE
                                Toast.makeText(
                                    context,
                                    it.errorMessage.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is Response.Loading -> {
                                binding?.progressBarLogin?.visibility = View.VISIBLE
                            }
                        }
                    }

                }

            }
                R.id.ForgotPassword-> navController.navigate(R.id.action_logIn_to_emailVerification)
                R.id.SignUpButtonLogIn->navController.navigate(R.id.action_logIn_to_signUp)
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

