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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentUploadLecturesBinding
import com.example.unacademy.viewmodel.UploadLectureViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import kotlinx.coroutines.launch
import java.util.*

class upload_lectures : Fragment(),View.OnClickListener {
    lateinit var binding : FragmentUploadLecturesBinding
    lateinit var uploadLectureViewModel: UploadLectureViewModel
    private var IMAGE_REQUEST_CODE=100
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadLectureViewModel=ViewModelProvider(this)[UploadLectureViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_upload_lectures, container, false)
        binding.lifecycleOwner=this
        binding.uploadLectureViewModel=uploadLectureViewModel
        binding.UploadLectureButton.setOnClickListener(this)
        binding.createLecture.setOnClickListener(this)
        return binding.root
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
            storageReference.putFile(imageUri).addOnSuccessListener{
                    it.storage.downloadUrl.addOnSuccessListener {
                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss()
                        }
                            uploadLectureViewModel.helperTextVideo.postValue(data?.data.toString())
                            uploadLectureViewModel.movieUrl.postValue(it.toString())
                    }
                }
                .addOnFailureListener(OnFailureListener()
                {
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss()
                    }
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
                })
                .addOnProgressListener(OnProgressListener()
                {
                    var progresPercent = (100*it.bytesTransferred/it.totalByteCount)
                    progressDialog.setMessage("Progress: " + progresPercent + "%")
                })
        }
    }
    private fun pickVideoGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="video/*"
        startActivityForResult(intent,IMAGE_REQUEST_CODE)
    }
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.UploadLectureButton->pickVideoGallery()
            R.id.createLecture-> {
                if (uploadLectureViewModel.Validations() == null) {
                    lifecycleScope.launch {
                        var result = uploadLectureViewModel.uploadLectures()

                        result.observe(viewLifecycleOwner,
                            {
                                when (it) {
                                    is Response.Success -> {
                                        Toast.makeText(
                                            context,
                                            "Lecture uploaded ",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        findNavController().navigate(R.id.action_upload_lectures_to_lecturesTeachersSide)
                                    }
                                    is Response.Error -> {
                                        Toast.makeText(
                                            context,
                                            it.errorMessage.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    is Response.Loading -> Toast.makeText(
                                        context,
                                        "Loading",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            })
                    }
                }
            }
        }
    }


}