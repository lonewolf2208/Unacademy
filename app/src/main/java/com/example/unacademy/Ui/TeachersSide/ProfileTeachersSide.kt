package com.example.unacademy.Ui.TeachersSide

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.unacademy.R
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentProfileTeachersSideBinding
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import com.example.unacademy.models.TeachersSideModels.getTeachersProfile.getTeachersProfileModel
import com.example.unacademy.models.TeachersSideModels.teachersProfileDataClass
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileTeachersSide : Fragment(),View.OnClickListener {
    companion object{
        var result:getTeachersProfileModel?=null
    }
   lateinit var binding:FragmentProfileTeachersSideBinding
    private var token:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_profile_teachers_side,container,false)
        binding.makeAnnouncement.setOnClickListener(this)
        binding.ViewProfile.setOnClickListener(this)
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
                            result=response.body()
                            binding?.setProfileImageTeachers?.load(response.body()?.picture)
                            binding.FacultyName.text=response.body()?.name.toString()
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

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.makeAnnouncement->findNavController().navigate(R.id.action_profileTeachersSide_to_makeAnnouncement2)
            R.id.ViewProfile->findNavController().navigate(R.id.action_profileTeachersSide_to_change_teachers_profile)
        }
    }

}