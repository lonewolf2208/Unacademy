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
import coil.load
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentCreateYourSeriesBinding
import com.example.unacademy.viewmodel.CreateSeriesViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import kotlinx.coroutines.launch
import java.util.*


class CreateYourSeries : Fragment() ,View.OnClickListener{
    private lateinit var imageUri: Uri
    lateinit var binding:FragmentCreateYourSeriesBinding
    lateinit var createYourSeriesViewModel: CreateSeriesViewModel
    private var IMAGE_REQUEST_CODE=100
    var storage: FirebaseStorage = FirebaseStorage.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createYourSeriesViewModel=ViewModelProvider(this)[CreateSeriesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =DataBindingUtil.inflate(inflater,R.layout.fragment_create_your_series, container, false)
        binding.lifecycleOwner=this
        binding.createSeriesViewModel=createYourSeriesViewModel
        binding.ThumbnailUpload.setOnClickListener(this)
        binding.createSeries.setOnClickListener(this)
        return binding.root
    }
    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,IMAGE_REQUEST_CODE)
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
            progressDialog.setCancelable(false)
            storageReference.putFile(imageUri)
                .addOnSuccessListener{
                    it.storage.downloadUrl.addOnSuccessListener {

                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss()
                        }
                        binding.ThumbnailSeries.visibility=View.VISIBLE
                            binding.ThumbnailSeries.setImageURI(data?.data)
                            createYourSeriesViewModel.icon.postValue(it.toString())

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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ThumbnailUpload -> pickImageGallery()
            R.id.createSeries -> {
                if (createYourSeriesViewModel.Validations() == null) {
                    if (createYourSeriesViewModel.icon.value.isNullOrEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Upload Image For Series",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        lifecycleScope.launch {
                            val result = createYourSeriesViewModel.createSeries()
                            result.observe(
                                this@CreateYourSeries
                            ) {
                                when (it) {
                                    is Response.Success -> {
                                        findNavController().navigate(R.id.action_createYourSeries_to_homePageTeachersSide)
                                        Toast.makeText(
                                            context,
                                            "Series Created",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                    is Response.Error -> Toast.makeText(
                                        context,
                                        it.errorMessage,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}