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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.Adapter.RecyclerAdapterLectureTeachersSide
import com.example.unacademy.Adapter.RecyclerAdapterTeachersSideHomePage
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentLecturesTeachersSideBinding
import com.example.unacademy.viewModel.HomePageViewModel
import com.example.unacademy.viewModel.LectureTeachersSideViewModel
import com.example.unacademy.viewModel.TeachersProfileViewModel
import kotlinx.coroutines.launch

class lecturesTeachersSide : Fragment() {

    lateinit var binding: FragmentLecturesTeachersSideBinding
    lateinit var lectureTeachersSideViewModel: LectureTeachersSideViewModel
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterLectureTeachersSide.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lectureTeachersSideViewModel =
            ViewModelProvider(this)[LectureTeachersSideViewModel::class.java]
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
            Toast.makeText(context,HomePageTeachersSide.seriesid.toString(),Toast.LENGTH_LONG).show()
        binding.lifecycleOwner = this
        binding.lectureSideViewModel = lectureTeachersSideViewModel
        lifecycleScope.launch {
            lectureTeachersSideViewModel.getLectures(HomePageTeachersSide.seriesid!!.toInt())
            lectureTeachersSideViewModel.result.observe(viewLifecycleOwner,
                {
                    when (it) {
                        is com.example.unacademy.Repository.Response.Success -> {
                            Toast.makeText(context,it.data.toString(),Toast.LENGTH_LONG).show()
                            layoutManager = LinearLayoutManager(container?.context)
                            binding.recyclerViewLectureSide.layoutManager = layoutManager
                            adapter = RecyclerAdapterLectureTeachersSide(it.data)
                            binding.recyclerViewLectureSide.adapter = adapter
//                        lectureTeachersSideViewModel.seriesName.postValue(it.data?.get(0)?.series_name)
//                        lectureTeachersSideViewModel.seriesDescription.postValue(it.data?.get(0)?.series_description)

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

                })
        }

        return binding.root
    }
}