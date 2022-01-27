package com.example.unacademy.Ui.Auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.unacademy.Activities.NavBarActivity
import com.example.unacademy.Activities.StudentSideActivity
import com.example.unacademy.R
import com.example.unacademy.Repository.AuthRepo.ChangePasswordRepo
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentChangePasswordBinding

class ChangePassword : Fragment() ,View.OnClickListener{
   private var _binding: FragmentChangePasswordBinding?=null

    private val binding
    get()=_binding
    private var validationFlagPassword1=0
    private var validationFlagPassword2=0
    private var ChangePasswordRepo: ChangePasswordRepo?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentChangePasswordBinding.inflate(inflater,container,false)
        binding?.doneButtonChangePassword?.setOnClickListener(this)
        passwordFocusListener()
        return binding?.root
    }
    private fun passwordFocusListener()
    {
        binding?.oldPasswordChangePassword?.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                var passText= binding?.oldPasswordChangePassword?.text.toString().trim()
                if(Validations.validPassword(passText) ==null){
                    binding!!.oldPasswordChangePasswordContainer.helperText=""
                    validationFlagPassword1=1
                }
                else {

                    binding!!.oldPasswordChangePasswordContainer.helperText =
                        Validations.validPassword(passText).toString()
                    validationFlagPassword1=0
                }
            }
        }
        binding?.ConfirmPasswordChangePassword?.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                var passText= binding?.ConfirmPasswordChangePassword?.text.toString().trim()
                if(Validations.validPassword(passText) ==null&& Validations.samePassword(passText,binding?.oldPasswordChangePassword?.text.toString().trim())==null){

                    binding!!.ConfirmPasswordChangePasswordContainer.helperText=""
                    validationFlagPassword2=1
                }
                else if(Validations.samePassword(passText,binding?.oldPasswordChangePassword?.text.toString())!=null)
                    {
                        binding!!.ConfirmPasswordChangePasswordContainer.helperText=Validations.samePassword(passText,binding?.oldPasswordChangePassword?.text.toString())
                                validationFlagPassword2=0
                    }
                else {

                    binding!!.ConfirmPasswordChangePasswordContainer.helperText =
                        Validations.validPassword(passText).toString()
                    validationFlagPassword2=0
                }
            }
        }
    }

    override fun onClick(v: View?) {
        var navController=findNavController()
        when(v?.id) {
            R.id.doneButtonChangePassword ->
            {
                binding?.oldPasswordChangePassword?.clearFocus()
                binding?.ConfirmPasswordChangePassword?.clearFocus()
                if(validationFlagPassword1==1  && validationFlagPassword2==1)
                {
                    ChangePasswordRepo= ChangePasswordRepo(RetrofitClient.init())
                    ChangePasswordRepo?.PasswordApi(EmailVerification.emailChangePassword, binding?.ConfirmPasswordChangePassword?.text.toString())
                    ChangePasswordRepo?.ChangePasswordResponse?.observe(this@ChangePassword,{
                        when (it) {
                            is Response.Success ->
                            {
                                binding?.progressBarChangePassword?.visibility=View.INVISIBLE
                                binding?.doneButtonChangePassword?.isEnabled=true
//                                var GetTokenRepo= GetTokenRepo(RetrofitClient.init())
//                                GetTokenRepo.getToken(EmailVerification.emailChangePassword,binding?.ConfirmPasswordChangePassword?.text.toString())
//                                Toast.makeText(context,com.example.unacademy.Repository.AuthRepo.GetTokenRepo.accessToken,
//                                    Toast.LENGTH_LONG).show()
//                                GetTokenRepo.TokenResponse.observe(this@CreatePassword,
//                                    {
//                                        Toast.makeText(context,com.example.unacademy.Repository.AuthRepo.GetTokenRepo.accessToken,Toast.LENGTH_LONG).show()
//                                    })
//                                binding.progressBarCreatePassword.visibility=View.INVISIBLE
                                Toast.makeText(context,"Password Created", Toast.LENGTH_LONG).show()
                                if(navController.previousBackStackEntry?.destination?.label.toString()=="fragment_log_in") {
                                    navController.navigate(R.id.logIn)
                                }
                                else if(navController.previousBackStackEntry?.destination?.label.toString()=="fragment_email_verification")
                                {
                                    val intent=Intent(activity,StudentSideActivity::class.java)
                                    startActivity(intent)
                                }
                                else
                                {
                                    Toast.makeText(context,navController.previousBackStackEntry?.destination?.label.toString(),Toast.LENGTH_LONG).show()
                                    val intent=Intent(activity,NavBarActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                            is Response.Error -> {
                                binding?.progressBarChangePassword?.visibility=View.INVISIBLE
                                binding?.doneButtonChangePassword?.isEnabled=true
//                                binding.progressBarCreatePassword.visibility=View.INVISIBLE
//                                binding.signInButtonCreatePassword.isEnabled=true
                                Toast.makeText(context,it.errorMessage.toString(), Toast.LENGTH_LONG).show()
                            }
                            is Response.Loading->
                            {
                                binding?.progressBarChangePassword?.visibility=View.VISIBLE
                                binding?.doneButtonChangePassword?.isEnabled=false
                            }

                        }
                    })
                }
                }
            }
        }
    }
