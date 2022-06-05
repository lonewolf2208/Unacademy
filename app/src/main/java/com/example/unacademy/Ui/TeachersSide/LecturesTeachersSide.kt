package com.example.unacademy.Ui.TeachersSide

import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.Activities.ExoPlayer
import com.example.unacademy.Adapter.RecyclerAdapterLectureTeachersSide
import com.example.unacademy.Adapter.RecyclerAdapterTeachersSideHomePage
import com.example.unacademy.Adapter.StudentSideAdapters.FragementChangeSeries
import com.example.unacademy.Adapter.StudentSideAdapters.FragmentStateChangeAdapter
import com.example.unacademy.R
import com.example.unacademy.Ui.StudentsSide.homePageStudentSide
import com.example.unacademy.databinding.FragmentLecturesTeachersSideBinding
import com.example.unacademy.viewmodel.LectureTeachersSideViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class LecturesTeachersSide : Fragment() {

    lateinit var binding: FragmentLecturesTeachersSideBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object{
        var videoUrl:String?=null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_lectures_teachers_side,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.seriedThumbnail.load(RecyclerAdapterLectureTeachersSide.seriesThumbnail.toString())
        binding.seriesName.setText(RecyclerAdapterLectureTeachersSide.series_name)
        binding.seriesDescription.setText(RecyclerAdapterLectureTeachersSide.seriesDescription)
        binding.viewPagerSeries.adapter= FragementChangeSeries(this@LecturesTeachersSide)
        TabLayoutMediator(binding.tabLayoutSeries,binding.viewPagerSeries) { tab, position ->
            when (position) {
                0 -> tab.text = "Lectures"
                1 -> tab.text = "Notes"
            }
        }.attach()

//        binding.floatingButtonUploadLecture.setOnClickListener {
//            findNavController().navigate(R.id.upload_lectures)
//        }


        return binding.root
    }
}