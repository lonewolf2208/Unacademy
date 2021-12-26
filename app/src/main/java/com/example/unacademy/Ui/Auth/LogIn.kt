package com.example.unacademy.Ui.Auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Repository.ApiRepo
//import com.example.unacademy.Repository.ApiRepo
import com.example.unacademy.Repository.Response
import com.example.unacademy.api.Api
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentLogInBinding
import com.example.unacademy.models.LoginDataClass
import com.example.unacademy.viewModel.LogInViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.create

//import com.example.unacademy.databinding.FragmentLogInBinding


class LogIn : Fragment() ,View.OnClickListener{
    private var binding:FragmentLogInBinding?=null
    private var validationFlagEmail = 0
    private var validationFlagPassword = 0
    lateinit var ApiRepo: ApiRepo
     var logInViewModel= LogInViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = FragmentLogInBinding.inflate(inflater,container,false)
//        val Api = RetrofitClient.getInstance().create(Api::class.java)
        ApiRepo= ApiRepo(RetrofitClient.init())
//        ApiRepo= ApiRepo(Api)
        emailFocusListener()
        passwordFocusListener()
        // Inflate the layout for this fragment
//        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_log_in,container,false)
//
//        binding.lifecycleOwner = this
//        binding.logInViewModel=LogInViewModel()
//
//        binding.ForgotPassword.setOnClickListener {
//            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.emailVerification) }
//        }
//        logInViewModel.loginEmail.observe(viewLifecycleOwner,
//            {
//
//            })
//        binding.LogInButton.setOnClickListener{
//            Toast.makeText(context,logInViewModel.loginEmail.value.toString(),Toast.LENGTH_LONG).show()
//            if(logInViewModel.Validations()==true)
//            {
//                view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.signUp) }
//            }
//            else{
//                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
//            }
//        }

        binding!!.SignUpButtonLogIn.setOnClickListener(this)
//        binding.lifecycleOwner = this
//        binding.logInViewModel=LogInViewModel()
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
                if (validationFlagEmail == 1 && validationFlagPassword == 1)
                {
                    binding?.LogInButton?.isEnabled = false
                        var LoginDataClass = LoginDataClass(emailText?.text.toString(), passText?.text.toString())
                        ApiRepo?.getLoginApi(emailText?.text.toString(),passText?.text.toString())
                        ApiRepo?.ApiResponse?.observe(this@LogIn,{
                            when (it) {

                                is Response.Success -> {
                                    binding?.progressBarLogin?.visibility=View.INVISIBLE

                                    Toast.makeText(context,"Logged In", Toast.LENGTH_LONG).show()

                                }
                                is Response.Error -> {
                                    binding?.LogInButton?.isEnabled = true
                                    binding?.progressBarLogin?.visibility=View.INVISIBLE
                                    Toast.makeText(context,it.errorMessage.toString(), Toast.LENGTH_LONG).show()
                                }
                                is Response.Loading->
                                {
                                    binding?.progressBarLogin?.visibility=View.VISIBLE
                                    Toast.makeText(context,"Lading", Toast.LENGTH_LONG).show()
                                }
                                else-> {
                                    binding?.LogInButton?.isEnabled = true
                                    Toast.makeText(context,"ElseBlock", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
//                        Toast.makeText(context,result.toString(),Toast.LENGTH_LONG).show()
//                        when (result) {
//                            is Response.Success<*> -> {
//                                Toast.makeText(context,"Su", Toast.LENGTH_LONG).show()
//                                navController.navigate(R.id.action_logIn_to_emailVerification)
//                            }
//                            is Response.Error<*> -> {
//                                Toast.makeText(context, result.errorMessage, Toast.LENGTH_LONG).show()
//                            }
//                            else->
//                            {
//                                Toast.makeText(context,"ElseBlock", Toast.LENGTH_LONG).show()
//                            }
//                        }


//                        var LoginDataClass =LoginDataClass(emailText?.text.toString(), passText?.text.toString())
//                        RetrofitClient.init().LoginApi(emailText?.text.toString(),passText?.text.toString()).enqueue(object : Callback<ResponseBody?> {
//                            override fun onResponse(
//                                call: Call<ResponseBody?>,
//                                response: retrofit2.Response<ResponseBody?>
//                            ) {
//                                if(response.isSuccessful())
//                                {
//                                    navController.navigate(R.id.action_logIn_to_emailVerification)
//                                }
//                                else if(response.code() == 401)
//                                {
//                                    Toast.makeText(context,"Incorrect Password",Toast.LENGTH_LONG).show()
//                                }
//                                else if(response.code()==406)
//                                {
//                                    Toast.makeText(context,"User not regisetred",Toast.LENGTH_LONG).show()
//                                }
//                                else
//                                {
//                                    Toast.makeText(context,response.message(),Toast.LENGTH_LONG).show()
//                                }
//                            }
//
//                            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
//                                Toast.makeText(context,"On Failure",Toast.LENGTH_LONG).show()
//                            }
//                    })
                }

            }
                R.id.ForgotPassword-> navController.navigate(R.id.action_logIn_to_emailVerification)
                R.id.SignUpButtonLogIn->navController.navigate(R.id.action_logIn_to_signUp)
            }
        }

    private fun passwordFocusListener()
    {
        binding?.LoginPassword?.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                var passText= binding?.LoginPassword?.text.toString().trim()
                if(Validations.validPassword(passText) ==null){
                    binding!!.LoginPasswordContainer.helperText=""
                    validationFlagPassword=1
                }
                else {

                    binding!!.LoginPasswordContainer.helperText =
                        Validations.validPassword(passText).toString()
                    validationFlagPassword=0
                }
            }
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

