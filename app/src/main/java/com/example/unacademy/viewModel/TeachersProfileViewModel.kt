package com.example.unacademy.viewModel


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.databinding.BindingAdapter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unacademy.Activities.MainActivity
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class TeachersProfileViewModel:ViewModel()
{

    var imageUri=MutableLiveData<Uri>()
    var name=MutableLiveData<String>()
    var mobileno=MutableLiveData<String>()
    var gender=MutableLiveData<Spinner>()
    var dateofbirth=MutableLiveData<String>()
    var educationdetails=MutableLiveData<String>()
    var experience = MutableLiveData<String>()

}