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
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.unacademy.R
import com.example.unacademy.Repository.StudentSideRepo.UpdateProfileRepo
import com.example.unacademy.api.Api
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentEditYourProfileStudentSideBinding
import com.example.unacademy.viewmodel.viewmodelStudentside.StudentProfileViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import java.util.*

class EditYourProfileStudentSide : Fragment() ,View.OnClickListener{

    lateinit var binding:FragmentEditYourProfileStudentSideBinding
    private lateinit var imageUri: Uri
    var checkBox:Int=0; lateinit var profileSTudentSideViewModel: StudentProfileViewModel
    private var IMAGE_REQUEST_CODE=100
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var forImage=0
    var imageClicked=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileSTudentSideViewModel=ViewModelProvider(this)[StudentProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_edit_your_profile_student_side, container, false)
        binding.SaveEdit.setOnClickListener(this)
        binding.setProfileImageStudentCardView.setOnClickListener(this)
        binding.SaveEdit.visibility=View.INVISIBLE
        binding.BioStudentProfile.isEnabled=false
        binding.ClassStudentProfile.isEnabled=false
        binding.DateOfBirthStudentPRofile.isEnabled=false
        binding.EmailAddressStudentProfile.isEnabled=false
        binding.GenderStudentsProfile.isEnabled=false
        binding.MobileNoStudentProfile.isEnabled=false
        binding.NameStudentProfileStudentSide.isEnabled=false
        binding.checkBoxEdit.setOnClickListener(this)
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

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.setProfileImageStudentCardView->{
                if(checkBox%2!=0)
                {
                    imageClicked=true
                    pickImageGallery()
                }
                else
                {
                    imageClicked=false
                }
            }
            R.id.checkBoxEdit->{
                checkBox++
                if(checkBox%2!=0)
                {
                    binding.setProfileImageStudentCardView.isEnabled=true
                    binding.SaveEdit.visibility=View.VISIBLE
                    binding.BioStudentProfile.isEnabled=true
                    binding.ClassStudentProfile.isEnabled=true
                    binding.DateOfBirthStudentPRofile.isEnabled=true
                    binding.EmailAddressStudentProfile.isEnabled=true
                    binding.GenderStudentsProfile.isEnabled=true
                    binding.MobileNoStudentProfile.isEnabled=true
                    binding.NameStudentProfileStudentSide.isEnabled=true

                }
                else
                {
                    binding.setProfileImageStudentCardView.isEnabled=false
                    binding.SaveEdit.visibility=View.INVISIBLE
                    binding.BioStudentProfile.isEnabled=false
                    binding.ClassStudentProfile.isEnabled=false
                    binding.DateOfBirthStudentPRofile.isEnabled=false
                    binding.EmailAddressStudentProfile.isEnabled=false
                    binding.GenderStudentsProfile.isEnabled=false
                    binding.MobileNoStudentProfile.isEnabled=false
                    binding.NameStudentProfileStudentSide.isEnabled=false
                }
            }
            R.id.SaveEdit->{
                if(!imageClicked)
                {
                    imageUri=ProfileStudentSide.studentProfile!!.picture.toUri()
                }
                Toast.makeText(requireContext(),"CLicked",Toast.LENGTH_LONG).show()
                lifecycleScope.launch {
                profileSTudentSideViewModel.UpdatePofile(
                    binding.NameStudentProfileStudentSide.text.toString(),
                    binding.GenderStudentsProfile.text.toString(),
                    binding.DateOfBirthStudentPRofile.text.toString(),
                    imageUri.toString(),
                    binding.ClassStudentProfile.text.toString(),
                    binding.MobileNoStudentProfile.text.toString().toLong(),
                    binding.BioStudentProfile.text.toString(),
                )
            }}
        }
    }
}