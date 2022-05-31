package com.example.unacademy.Ui.StudentsSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.models.SlideModel
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentStudentStorySliderBinding
import com.example.unacademy.viewmodel.viewmodelStudentside.StoryInfoViewModel
import kotlinx.coroutines.launch


class student_story_slider : Fragment() {
    lateinit var binding:FragmentStudentStorySliderBinding
    lateinit var storyInfoViewModel: StoryInfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storyInfoViewModel=ViewModelProvider(this)[StoryInfoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_student_story_slider, container, false)
        binding.lifecycleOwner=this
        binding.storyInfoViewModel=storyInfoViewModel
        lifecycleScope.launch {
            var result= storyInfoViewModel.StudentStoryInfo()
            result.observe(viewLifecycleOwner
            ) {
                when (it) {
                    is Response.Success -> {
                        var size = it.data?.size?.toInt()
                        var imageList: MutableList<String>? = null
                        var slideModel = ArrayList<SlideModel>()
                        for (i in 0..(size!!.toInt() - 1)) {
                            slideModel.add(SlideModel(it.data!![i].doc, centerCrop = true))
                        }
                        binding.imageSliderFragmentStudentStory.setImageList(slideModel, true)
                    }
                    is Response.Error -> {
                        Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
        return binding.root
    }


}