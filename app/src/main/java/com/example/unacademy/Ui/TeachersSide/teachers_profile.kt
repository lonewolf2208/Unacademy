package com.example.unacademy.Ui.TeachersSide

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentTeachersProfileBinding
import com.example.unacademy.viewModel.TeachersProfileViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.UploadTask
import java.util.*

class teachers_profile : Fragment() {
    private lateinit var imageUri: Uri
    private var IMAGE_REQUEST_CODE=100
    lateinit var binding: FragmentTeachersProfileBinding
    lateinit var teachersProfileViewModel: TeachersProfileViewModel
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var forImage=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_teachers_profile,container,false)
        binding.lifecycleOwner=this
        binding.teachersProfile=TeachersProfileViewModel()
        binding.teachersImage.setOnClickListener {
            pickImageGallery()
        }
        binding.VideoUpload.setOnClickListener {

            pickVideoGallery()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teachersProfileViewModel=ViewModelProvider(this)[TeachersProfileViewModel::class.java]
    }
    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,IMAGE_REQUEST_CODE)
        forImage=1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==IMAGE_REQUEST_CODE && resultCode== Activity.RESULT_OK)
        {
            imageUri= data?.getData()!!
            var randomKey = UUID.randomUUID().toString()
            var storageReference = storage.getReference("images/"+randomKey)
            var progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading File ")
            progressDialog.show()
            storageReference.putFile(imageUri)
                .addOnSuccessListener{
                    it.storage.downloadUrl.addOnSuccessListener {
                        val url = it.toString()
                       if(progressDialog.isShowing())
                       {
                           progressDialog.dismiss()
                       }
                    }
                }
                .addOnFailureListener(OnFailureListener()
                {
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss()
                    }
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                })
                .addOnProgressListener(OnProgressListener()
                {
                    var progresPercent = (100*it.bytesTransferred/it.totalByteCount)
                    progressDialog.setMessage("Progress: " + progresPercent + "%")
                })
            if(forImage==1) {
                binding?.teachersImage?.setImageURI(data?.data)
            }
            TeachersProfileViewModel().imageUri.postValue(imageUri)
        }
    }
    private fun pickVideoGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="video/*"
        startActivityForResult(intent,IMAGE_REQUEST_CODE)
    }
}