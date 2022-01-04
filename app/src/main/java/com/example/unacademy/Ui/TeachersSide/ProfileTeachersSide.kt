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
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentProfileTeachersSideBinding
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase





class ProfileTeachersSide : Fragment() {

    private var binding:FragmentProfileTeachersSideBinding?=null
    private var imageUri:Uri?=null
    private var IMAGE_REQUEST_CODE=100
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileTeachersSideBinding.inflate(inflater,container,false)
        binding!!.setProfileImageTeachers.setOnClickListener {
            pickImageGallery()
        }
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!").addOnCompleteListener{
            Toast.makeText(context,"Saved",Toast.LENGTH_LONG).show()
        }
        return binding!!.root
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,IMAGE_REQUEST_CODE)
    }

    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==IMAGE_REQUEST_CODE && resultCode==RESULT_OK)
        {
            imageUri= data?.getData()
            binding?.setProfileImageTeachers?.setImageURI(data?.data)
        }
    }
}