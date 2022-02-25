package com.example.unacademy.Ui.StudentsSide

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
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentEditYourProfileStudentSideBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import java.util.*

class EditYourProfileStudentSide : Fragment() {

    lateinit var binding:FragmentEditYourProfileStudentSideBinding
    private lateinit var imageUri: Uri
    private var IMAGE_REQUEST_CODE=100
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var forImage=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_edit_your_profile_student_side, container, false)

        binding.setProfileImageStudentCardView.setOnClickListener {
            pickImageGallery()
        }
       binding.BioStudentProfile.setText(ProfileStudentSide.studentProfile!!.bio.toString())
        binding.ClassStudentProfile.setText(ProfileStudentSide.studentProfile!!.standard)
        binding.DateOfBirthStudentPRofile.setText(ProfileStudentSide.studentProfile!!.birth)
        binding.EmailAddressStudentProfile.setText(ProfileStudentSide.studentProfile!!.student.email)
        binding.GenderStudentsProfile.setText(ProfileStudentSide.studentProfile!!.gender.toString())
        binding.MobileNoStudentProfile.setText(ProfileStudentSide.studentProfile!!.mobile.toString())
        binding.NameStudentProfileStudentSide.setText(ProfileStudentSide.studentProfile!!.name.toString())
        binding.setProfileImageStudentCardView.load(ProfileStudentSide.studentProfile!!.picture.toString())
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
            progressDialog.setCancelable(false)
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
                            binding?.setProfileImageStudentCardView?.setImageURI(data?.data)
//                            teachersProfileViewModel.imageUrl.postValue(it.toString())
                            forImage=0
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
}