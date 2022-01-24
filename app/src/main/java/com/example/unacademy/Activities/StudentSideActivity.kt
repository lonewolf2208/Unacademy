package com.example.unacademy.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.unacademy.R
import com.example.unacademy.databinding.ActivityStudentSideBinding

lateinit var binding:ActivityStudentSideBinding
class StudentSideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStudentSideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_student_side) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        binding.bottomNavigationViewStudentSide.setupWithNavController(navController)
    }
}