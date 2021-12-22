package com.example.unacademy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.example.unacademy.viewModel.MainViewModel
import com.example.unacademy.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    lateinit var MainViewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
//        Navigation.findNavController(this,R.id.mainActivity).navigate(R.id.logIn)
        setContentView(R.layout.activity_main)
    }
}