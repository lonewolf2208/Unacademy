package com.example.unacademy.Ui.TeachersSide

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentProfileTeachersSideBinding
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase





class ProfileTeachersSide : Fragment() {

    private var binding:FragmentProfileTeachersSideBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileTeachersSideBinding.inflate(inflater,container,false)
        binding!!.setProfileImageTeachers.load(teachers_profile.resultteachers_profile?.picture)
        binding!!.FacultyQualification.text=teachers_profile.resultteachers_profile?.qual
        binding!!.FacultyName.text=teachers_profile.resultteachers_profile?.name
        binding!!.facultyExperience.text=teachers_profile.resultteachers_profile?.bio
        return binding!!.root
    }

}