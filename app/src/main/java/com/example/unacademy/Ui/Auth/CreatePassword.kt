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
import com.example.unacademy.Repository.GetTokenRepo
import com.example.unacademy.Repository.PasswordRepo
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentCreatePasswordBinding
import kotlinx.coroutines.launch

class CreatePassword : Fragment() ,View.OnClickListener{
    private var _binding : FragmentCreatePasswordBinding?=null
    private val binding
    get() = _binding!!
    private var validationFlagPassword1 =0
    private var validationFlagPassword2=0

    private var passwordRepo:PasswordRepo?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCreatePasswordBinding.inflate(inflater, container, false)

        binding.signInButtonCreatePassword.setOnClickListener(this)
        passwordFocusListener()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onClick(v: View?) {
        val navController = findNavController()
        when(v?.id)
        {

            R.id.signInButtonCreatePassword-> {
                var email=SignUp.email
                var name=SignUp.name
                binding.ConfirmPasswordCreatePassword.clearFocus()
                binding.NewPasswordCreatePassword.clearFocus()
                if(validationFlagPassword1==1 && validationFlagPassword2==1) {
                    binding.signInButtonCreatePassword.isEnabled=false
                    passwordRepo= PasswordRepo(RetrofitClient.init())
                    passwordRepo?.PasswordApi(name,email,binding.ConfirmPasswordCreatePassword.text.toString())
                    passwordRepo?.PasswordResponse?.observe(this@CreatePassword,{
                        when (it) {
                            is Response.Success ->
                            {
                                var GetTokenRepo=GetTokenRepo(RetrofitClient.init())
                                GetTokenRepo.getToken(email,binding.ConfirmPasswordCreatePassword.text.toString())
//                                Toast.makeText(context,GetTokenRepo.TokenResponse.value.toString(),Toast.LENGTH_LONG).show()
                                GetTokenRepo.TokenResponse.observe(this@CreatePassword,
                                    {
                                        when(it)
                                        {
                                            is Response.Success->
                                            {
                                               lifecycleScope.launch {
                                                   Splash_Screen.saveInfo("access",it.data?.access.toString())
                                                   Splash_Screen.saveInfo("refresh",it.data?.refresh.toString())
                                               }
                                                navController.navigate(R.id.action_createPassword_to_logIn)}
                                            is Response.Loading->Toast.makeText(context,"Loading",Toast.LENGTH_LONG).show()
                                            is Response.Error->Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
                                        }
                                    })
                                binding.progressBarCreatePassword.visibility=View.INVISIBLE
                                Toast.makeText(context,"LoggedIn", Toast.LENGTH_LONG).show()
//                                navController.navigate(R.id.action_createPassword_to_logIn)
                            }
                            is Response.Error -> {
                                binding.progressBarCreatePassword.visibility=View.INVISIBLE
                                binding.signInButtonCreatePassword.isEnabled=true
                                Toast.makeText(context,it.errorMessage.toString(), Toast.LENGTH_LONG).show()
                            }
                            is Response.Loading->
                            {
                                binding.progressBarCreatePassword.visibility=View.VISIBLE
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


    private fun passwordFocusListener()
    {
        binding?.NewPasswordCreatePassword?.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                var passText= binding?.NewPasswordCreatePassword?.text.toString().trim()
                if(Validations.validPassword(passText) ==null){
                    binding!!.NewPasswordCreatePasswordContainer.helperText=""
                    validationFlagPassword1=1
                }
                else {

                    binding!!.NewPasswordCreatePasswordContainer.helperText =
                        Validations.validPassword(passText).toString()
                    validationFlagPassword1=0
                }
            }
        }
        binding?.ConfirmPasswordCreatePassword?.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                var passText= binding?.ConfirmPasswordCreatePassword?.text.toString().trim()
                if(Validations.validPassword(passText) ==null){
                    binding!!.ConfirmPasswordCreatePasswordContainer.helperText=""
                    validationFlagPassword2=1
                }
                else {

                    binding!!.ConfirmPasswordCreatePasswordContainer.helperText =
                        Validations.validPassword(passText).toString()
                    validationFlagPassword2=0
                }
            }
        }
    }

}