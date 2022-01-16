package com.example.unacademy.Ui.TeachersSide

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import coil.load
import com.example.unacademy.R
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentProfileTeachersSideBinding
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import com.example.unacademy.models.TeachersSideModels.getTeachersProfile.getTeachersProfileModel
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileTeachersSide : Fragment() {

    private var binding:FragmentProfileTeachersSideBinding?=null
    private var token:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileTeachersSideBinding.inflate(inflater,container,false)
            lifecycleScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
                RetrofitClient.init().getTeachersProfile("Bearer ${token}").enqueue(object : Callback<getTeachersProfileModel?>{
                    override fun onResponse(
                        call: Call<getTeachersProfileModel?>,
                        response: Response<getTeachersProfileModel?>
                    ) {
                        if(response.isSuccessful)
                        {
                            binding?.setProfileImageTeachers?.load(response.body()?.picture)
                        }
                        else
                        {
                            Toast.makeText(context,response.message().toString(),Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<getTeachersProfileModel?>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }

        return binding!!.root
    }

}