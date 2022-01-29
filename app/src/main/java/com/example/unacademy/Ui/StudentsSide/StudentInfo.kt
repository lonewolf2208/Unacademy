package com.example.unacademy.Ui.StudentsSide

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
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.unacademy.Activities.StudentSideActivity
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentStudentInfoBinding
import com.example.unacademy.viewmodel.viewmodelStudentside.CreateStudentViewModel

import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import kotlinx.coroutines.launch
import java.util.*


class StudentInfo : Fragment(),View.OnClickListener,DatePickerDialog.OnDateSetListener{
    lateinit var binding:FragmentStudentInfoBinding
    lateinit var createStudentViewModel: CreateStudentViewModel
    private lateinit var imageUri: Uri
    private var IMAGE_REQUEST_CODE=100
    var storage: FirebaseStorage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       createStudentViewModel=ViewModelProvider(this)[CreateStudentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_student_info, container, false)
        binding.lifecycleOwner=this
        binding.createStudentViewModel=createStudentViewModel
        binding.shapeableImageView.setOnClickListener(this)

        binding.DobCalendar.setOnClickListener(this)
        binding.submitStudentInfo.setOnClickListener(this)
        binding.spinnerStudent.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)
                createStudentViewModel.gender.postValue(item.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
        return binding .root
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.getData()!!
            var randomKey = UUID.randomUUID().toString()
            var storageReference = storage.getReference("images/" + randomKey)
            var progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading File ")
            progressDialog.show()
            storageReference.putFile(imageUri)
                .addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss()
                        }
                        binding?.shapeableImageView?.load(data?.data)
                        createStudentViewModel.picture.postValue(it.toString())
                    }
                }
                .addOnFailureListener(OnFailureListener()
                {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss()
                    }
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                })
                .addOnProgressListener(OnProgressListener()
                {
                    var progresPercent = (100 * it.bytesTransferred / it.totalByteCount)
                    progressDialog.setMessage("Progress: " + progresPercent + "%")
                })
        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.shapeableImageView -> pickImageGallery()
            R.id.submitStudentInfo -> {
                if (createStudentViewModel.validations() == null) {
                    lifecycleScope.launch {
                        var result = createStudentViewModel.createStudent()
                        result.observe(viewLifecycleOwner, {
                            when (it) {
                                is Response.Success -> {
                                    val intent = Intent(activity, StudentSideActivity::class.java)
                                    startActivity(intent)
                                }
                                is Response.Error -> Toast.makeText(
                                    context,
                                    it.errorMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    }
                }
            }
            R.id.DobCalendar -> {
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
        binding.createStudentViewModel?.dob?.value=date
    }
}