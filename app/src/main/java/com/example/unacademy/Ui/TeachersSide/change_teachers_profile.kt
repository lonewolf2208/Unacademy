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
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.unacademy.Activities.NavBarActivity
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.databinding.FragmentChangeTeachersProfileBinding
import com.example.unacademy.databinding.FragmentTeachersProfileBinding
import com.example.unacademy.viewModel.ChangeTeachersProfileViewModel
import com.example.unacademy.viewModel.TeachersProfileViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import kotlinx.coroutines.launch
import java.util.*

class change_teachers_profile : Fragment(),View.OnClickListener{
    private lateinit var imageUri: Uri
    private var IMAGE_REQUEST_CODE=100
    lateinit var binding:FragmentChangeTeachersProfileBinding
    private lateinit var changeteachersProfileViewModel: ChangeTeachersProfileViewModel

    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var forImage=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       changeteachersProfileViewModel= ViewModelProvider(this)[ChangeTeachersProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_change_teachers_profile,container,false)
        binding.lifecycleOwner=this
        binding.teachersProfileViewModel=changeteachersProfileViewModel
        binding.changeTeachersImage.load(ProfileTeachersSide.result?.picture.toString())
        binding.changeTeachersImage.setOnClickListener(this)
        binding.VideoUploadChange.setOnClickListener(this)
        binding.ChangesubmitButtonTeachersProfile.setOnClickListener(this)
        binding.changeGender.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)
                changeteachersProfileViewModel.gender.postValue(item.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
        return binding.root
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
                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss()
                        }
                        if(forImage==1)
                        {
                            changeteachersProfileViewModel.imageUrl.postValue(it.toString())
                            forImage=0
                        }
                        else
                        {
                            changeteachersProfileViewModel.VideoUrl.postValue(it.toString())
                            binding.VideoUploadContainer.helperText=data.dataString
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
            R.id.change_teachers_image->pickImageGallery()
            R.id.VideoUploadChange->pickVideoGallery()
            R.id.ChangesubmitButtonTeachersProfile-> {
                if (changeteachersProfileViewModel.validations() == null) {
                    lifecycleScope.launch {
                       changeteachersProfileViewModel.submitData()
                    }
                    changeteachersProfileViewModel.result.observe(viewLifecycleOwner,
                        {
                            when (it) {
                                is Response.Success -> {
                                    Toast.makeText(context,"Information Updated",Toast.LENGTH_LONG).show()
                                    val intent = Intent(
                                        activity,
                                        NavBarActivity::class.java
                                    )
                                    startActivity(intent)
                                }
                                is Response.Error -> Toast.makeText(
                                    context,
                                    it.errorMessage.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
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
