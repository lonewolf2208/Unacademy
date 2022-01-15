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
import androidx.lifecycle.viewModelScope
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.databinding.FragmentTeachersProfileBinding
import com.example.unacademy.viewModel.TeachersProfileViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.util.*

class teachers_profile : Fragment(),View.OnClickListener {
    private lateinit var imageUri: Uri
    private var IMAGE_REQUEST_CODE=100
    lateinit var binding: FragmentTeachersProfileBinding
    lateinit var teachersProfileViewModel:TeachersProfileViewModel
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var forImage=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teachersProfileViewModel=ViewModelProvider(this)[TeachersProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_teachers_profile,container,false)
        binding.lifecycleOwner=this
        binding.teachersProfileViewModel=teachersProfileViewModel
        binding.teachersImage.setOnClickListener(this)
        binding.VideoUpload.setOnClickListener(this)
        binding.sunmitButtonTeachersProfile.setOnClickListener(this)
//        binding.spinner.setOnItemClickListener { parent, view, position, id ->  teachersProfileViewModel.gender.postValue(binding.spinner.selectedItem.toString())}
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
                            binding?.teachersImage?.setImageURI(data?.data)
                            teachersProfileViewModel.imageUrl.postValue(it.toString())
                            Toast.makeText(context,it.toString().length.toString(),Toast.LENGTH_LONG).show()
                            forImage=0
                        }
                        else
                        {
                            teachersProfileViewModel.VideoUrl.postValue(it.toString())
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
            R.id.teachers_image->pickImageGallery()
            R.id.VideoUpload->pickVideoGallery()
            R.id.sunmitButtonTeachersProfile->
            {
                Toast.makeText(context,teachersProfileViewModel.imageUrl.value.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(context,teachersProfileViewModel.name.value.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(context,teachersProfileViewModel.mobileno.value.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(context,teachersProfileViewModel.dateofbirth.value.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(context,teachersProfileViewModel.gender.value.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(context,teachersProfileViewModel.educationdetails.value.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(context,teachersProfileViewModel.experience.value.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(context,teachersProfileViewModel.VideoUrl.value.toString(),Toast.LENGTH_LONG).show()

                if(teachersProfileViewModel.validations()==null) {
                    lifecycleScope.launch {
                        var result = teachersProfileViewModel.submitData()
                        result.observe(this@teachers_profile, {
                            when (it) {
                                is Response.Success -> Toast.makeText(
                                    context,
                                    "Success",
                                    Toast.LENGTH_LONG
                                ).show()
                                is Response.Error -> Toast.makeText(
                                    context,
                                    it.errorMessage,
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
}