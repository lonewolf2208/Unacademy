package com.example.unacademy.Ui.TeachersSide

import android.app.Activity
import android.app.DatePickerDialog
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
import com.example.unacademy.Activities.NavBarActivity
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentTeachersProfileBinding
import com.example.unacademy.viewmodel.TeachersProfileViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import kotlinx.coroutines.launch
import java.util.*
import android.widget.AdapterView
import android.widget.DatePicker
import com.example.unacademy.Ui.Auth.Splash_Screen


class teachers_profile : Fragment(),View.OnClickListener,DatePickerDialog.OnDateSetListener {
    private lateinit var imageUri: Uri
    private var IMAGE_REQUEST_CODE=100
    lateinit var binding: FragmentTeachersProfileBinding
    private lateinit var teachersProfileViewModel:TeachersProfileViewModel

    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var forImage=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teachersProfileViewModel= ViewModelProvider(this)[TeachersProfileViewModel::class.java]
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
        binding.DobTeachersSide.setOnClickListener(this)
        binding.VideoUpload.setOnClickListener(this)
        binding.sunmitButtonTeachersProfile.setOnClickListener(this)
        binding.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)
                teachersProfileViewModel.gender.postValue(item.toString())
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
                            binding?.teachersImage?.setImageURI(data?.data)
                            teachersProfileViewModel.imageUrl.postValue(it.toString())
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
            R.id.sunmitButtonTeachersProfile-> {
                if (teachersProfileViewModel.validations() == null) {
                    lifecycleScope.launch {
                        teachersProfileViewModel.submitData()
                    }
                    teachersProfileViewModel.result.observe(viewLifecycleOwner,
                        {
                            when (it) {
                                is Response.Success -> {
                                    lifecycleScope.launch {
                                        Splash_Screen.save("teacherloggedIn",true)
                                    }
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
            R.id.DobTeachersSide->
            {
                var calendar = Calendar.getInstance()
                var year = calendar.get(Calendar.YEAR)
                var month = calendar.get(Calendar.MONTH)
                var day = calendar.get(Calendar.DAY_OF_MONTH)
                var datePickerDialog=DatePickerDialog(requireContext(),this,year,month,day)
                datePickerDialog.show()
            }
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var Selectedmonth=month.toString()
        var SelectedDate=dayOfMonth.toString()

        if(month/10==0)
        {
            Selectedmonth="0"+(month+1).toString()
        }
        if(dayOfMonth/10==0)
        {
            Toast.makeText(context,"dsadasdadsad",Toast.LENGTH_LONG).show()
            SelectedDate="0"+(dayOfMonth).toString()
        }
        var date=year.toString()+"-" + (Selectedmonth).toString()+"-"+SelectedDate.toString()
        binding.teachersProfileViewModel.dateofbirth.value=date
    }
}
//lifecycleScope.launch {
//    profileTeachersSideVIewModel.UploadStory()
//    profileTeachersSideVIewModel.result.observe(
//        viewLifecycleOwner, {
//            when(it)
//            {
//                is com.example.unacademy.Repository.Response.Success ->
//                {
//                    Toast.makeText(
//                        context,
//                        "Story Updated",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//                is com.example.unacademy.Repository.Response.Error -> Toast.makeText(
//                    context,
//                    it.errorMessage.toString(),
//                    Toast.LENGTH_LONG
//                ).show()
//                is com.example.unacademy.Repository.Response.Loading -> Toast.makeText(
//                    context,
//                    "Loading",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//    )
//}
//story=1
//}


