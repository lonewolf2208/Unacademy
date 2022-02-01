package com.example.unacademy.Ui.TeachersSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentChangePasswordInsideBinding
import com.example.unacademy.viewmodel.ChangePasswordInsideViewModel
import kotlinx.coroutines.launch


class Change_Password_Inside : Fragment(),View.OnClickListener {

    lateinit var binding:FragmentChangePasswordInsideBinding
    lateinit var changePasswordInsideViewModel: ChangePasswordInsideViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       changePasswordInsideViewModel=ViewModelProvider(this)[ChangePasswordInsideViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_change__password__inside, container, false)
        binding.lifecycleOwner=this
        binding.changePasswordInsideViewModel=changePasswordInsideViewModel
        binding.ChangePasswordInside.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ChangePasswordInside -> {
                if (changePasswordInsideViewModel.Validations() == null) {
                    lifecycleScope.launch {
                        var result = changePasswordInsideViewModel.changePassword()
                        result.observe(viewLifecycleOwner,
                            {
                                when (it) {
                                    is Response.Success -> {
                                        Toast.makeText(
                                            requireContext(),
                                            "Password Changed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        findNavController().navigate(R.id.change_Password_Inside)
                                    }
                                    is Response.Error -> {
                                        Toast.makeText(
                                            requireContext(),
                                            it.errorMessage.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            })
                    }
                }
            }
        }
    }
}