package com.example.unacademy.Ui.TeachersSide

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.unacademy.Activities.StoryActivity
import com.example.unacademy.R
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentProfileTeachersSideBinding
import com.example.unacademy.models.TeachersSideModels.getTeachersProfile.getTeachersProfileModel
import com.example.unacademy.viewmodel.ProfileTeachersSideVIewModel
import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ProfileTeachersSide : Fragment(),View.OnClickListener {
    companion object{
        lateinit var teachersInfo:getTeachersProfileModel
    }
    private lateinit var imageUri: Uri
    private var IMAGE_REQUEST_CODE=100
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
   lateinit var binding:FragmentProfileTeachersSideBinding
   lateinit var profileTeachersSideVIewModel:ProfileTeachersSideVIewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileTeachersSideVIewModel= ViewModelProvider(this)[com.example.unacademy.viewmodel.ProfileTeachersSideVIewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_profile_teachers_side,container,false)


        binding.lifecycleOwner=this
        binding.profileteacherSideViewModel=profileTeachersSideVIewModel

        lifecycleScope.launch {
            var result = profileTeachersSideVIewModel.GetProfile()
            result.observe(viewLifecycleOwner,
                {
                    when (it)
                    {
                        is com.example.unacademy.Repository.Response.Success -> {
                            binding?.setProfileImageTeachersCardView?.load(it.data!!.picture)
                            binding.FacultyName.text= it.data!!.name.toString()
                            teachersInfo = it.data!!
                        }
                        is com.example.unacademy.Repository.Response.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong please try again",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })

        }
        binding.makeAnnouncement.setOnClickListener(this)
        binding.setProfileImageTeachersCardView.setOnClickListener(this)
        binding.ViewProfile.setOnClickListener(this)
        binding.AddQuiz.setOnClickListener(this)
        binding.UploadStory.setOnClickListener(this)




        return binding!!.root
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
            storageReference.putFile(imageUri)
                .addOnSuccessListener{
                    it.storage.downloadUrl.addOnSuccessListener {
                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss()
                        }
                        lifecycleScope.launch {
                            profileTeachersSideVIewModel.doc.value=it.toString()
                            profileTeachersSideVIewModel.UploadStory()
                            profileTeachersSideVIewModel.result.observe(
                                viewLifecycleOwner, {
                                    when (it) {
                                        is com.example.unacademy.Repository.Response.Success -> {
                                            binding.setProfileImageTeachersCardView.setStrokeColorResource(R.color.black)
                                            Toast.makeText(
                                                context,
                                                "Story Updated",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                        is com.example.unacademy.Repository.Response.Error -> Toast.makeText(
                                            context,
                                            it.errorMessage.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
                                        is com.example.unacademy.Repository.Response.Loading -> Toast.makeText(
                                            context,
                                            "Loading",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            )
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
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.makeAnnouncement->findNavController().navigate(R.id.action_profileTeachersSide_to_makeAnnouncement2)
            R.id.ViewProfile->findNavController().navigate(R.id.action_profileTeachersSide_to_change_teachers_profile)
            R.id.UploadStory->
            {
                pickImageGallery()
            }
            R.id.setProfileImageTeachersCardView->
            {
                var intent=Intent(activity,StoryActivity::class.java)
                startActivity(intent)
            }
            R.id.AddQuiz->findNavController().navigate(R.id.action_profileTeachersSide_to_createAQuizTeacherSide)
        }
    }

}