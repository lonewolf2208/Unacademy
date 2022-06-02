package com.example.unacademy.Activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.unacademy.R
import com.example.unacademy.databinding.ActivityLectureBinding
import com.example.unacademy.databinding.ActivityNavBarBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import java.util.*

class LectureActivity : AppCompatActivity() {
    private lateinit var pdfUri: Uri
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var PDF_REQUEST_CODE=100
    private lateinit var binding:ActivityLectureBinding
    private val rotateOpen:Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim)}
    private val rotateClose:Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim)}
    private val fromBottom:Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim)}
    private val toBottom:Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim)}
    private var clicked =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLectureBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewLecture) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        binding.floatingButtonUploadLecture.setOnClickListener {
            navController.navigate(R.id.upload_lectures)
            binding.DocumentFloatingButton.startAnimation(toBottom)
            binding.floatingButtonUploadLecture.startAnimation(toBottom)
            binding.floatingButton.startAnimation(rotateClose)
        }
        binding.floatingButton.setOnClickListener {
            onAddButtonClicked()
        }
        binding.DocumentFloatingButton.setOnClickListener {
            pickPdf()
        }
        setContentView(binding.root)
    }
    private fun pickPdf() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type="application/*"
//        startActivityForResult(intent,PDF_REQUEST_CODE)
        startActivityForResult(intent,PDF_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PDF_REQUEST_CODE && resultCode== Activity.RESULT_OK)
        {
            pdfUri= data?.getData()!!
            var randomKey = UUID.randomUUID().toString()
            var storageReference = storage.getReference("studyMaterial/"+randomKey)
            var progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading File : ")
            progressDialog.show()
            progressDialog.setCancelable(false)
            storageReference.putFile(pdfUri)
                .addOnSuccessListener{
                    it.storage.downloadUrl.addOnSuccessListener {
                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss()
                        }
//                        binding.ThumbnailSeries.setImageURI(data?.data)
//                        createYourSeriesViewModel.icon.postValue(it.toString())

                    }
                }
                .addOnFailureListener(OnFailureListener()
                {
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss()
                    }
//                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
                })
                .addOnProgressListener(OnProgressListener()
                {
                    var progresPercent = (100*it.bytesTransferred/it.totalByteCount)
                    progressDialog.setMessage("Progress: " + progresPercent + "%")
                })
        }
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setCLickable(clicked)
        clicked=!clicked
    }

    private fun setVisibility(clicked:Boolean) {
        if(!clicked)
        {
            binding.DocumentFloatingButton.visibility= View.VISIBLE
            binding.floatingButtonUploadLecture.visibility=View.VISIBLE
        }
        else
        {
            binding.DocumentFloatingButton.visibility= View.INVISIBLE
            binding.floatingButtonUploadLecture.visibility=View.INVISIBLE   
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked)
        {
            binding.DocumentFloatingButton.startAnimation(fromBottom)
            binding.floatingButtonUploadLecture.startAnimation(fromBottom)
            binding.floatingButton.startAnimation(rotateOpen)
        }
        else
        {
            binding.DocumentFloatingButton.startAnimation(toBottom)
            binding.floatingButtonUploadLecture.startAnimation(toBottom)
            binding.floatingButton.startAnimation(rotateClose)
        }
    }
    private fun setCLickable(clicked: Boolean)
    {
        if(!clicked)
        {
            binding.DocumentFloatingButton.isClickable=true
            binding.floatingButtonUploadLecture.isClickable=true
        }
        else
        {
            binding.DocumentFloatingButton.isClickable=false
            binding.floatingButtonUploadLecture.isClickable=false
        }
    }
}