package com.example.unacademy.Ui.TeachersSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.example.unacademy.R
import com.example.unacademy.Repository.Response

import com.example.unacademy.databinding.FragmentStorySliderBinding
import com.example.unacademy.viewmodel.GetStoryViewModel
import kotlinx.coroutines.launch


class lecture_slider : Fragment() {

lateinit var binding:FragmentStorySliderBinding
lateinit var getStoryViewModel: GetStoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getStoryViewModel=ViewModelProvider(this)[GetStoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_story_slider, container, false)
        binding.lifecycleOwner=this
        binding.getStoryViewModel=getStoryViewModel
        lifecycleScope.launch {
           var result=  getStoryViewModel.getStory()
            result.observe(viewLifecycleOwner,
                {
                    when(it) {
                        is Response.Success -> {
                            var size = it.data?.size?.toInt()
                            var slideModel = ArrayList<SlideModel>()
                            for (i in 0..(size!!.toInt()-1)) {
                                slideModel.add(SlideModel(it.data!![i].doc, centerCrop = true ))
                            }
                            binding.imageSliderFragment.setImageList(slideModel, true)
                        }
                        is Response.Error -> {
                            Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_LONG)
                                .show()
                        }
                        is Response.noStory -> {
                           binding.storySliderText.text="No story to show"
                        }

                    }
                })
        }

        return binding.root
    }

}