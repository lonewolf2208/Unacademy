package com.example.unacademy.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.databinding.ActivityLectureBinding
import com.example.unacademy.databinding.ActivityNavBarBinding

class LectureActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLectureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLectureBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewLecture) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        binding.floatingButtonUploadLecture.setOnClickListener {
            navController.navigate(R.id.upload_lectures)
        }
        setContentView(binding.root)
    }
}