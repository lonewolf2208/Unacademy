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
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentLecturesTeachersSideBinding
import com.example.unacademy.viewmodel.LectureTeachersSideViewModel
import kotlinx.coroutines.launch

class lecturesTeachersSide : Fragment() {

    lateinit var binding: FragmentLecturesTeachersSideBinding
    lateinit var lectureTeachersSideViewModel: LectureTeachersSideViewModel
    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var adapter: RecyclerAdapterLectureTeachersSide

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lectureTeachersSideViewModel =
            ViewModelProvider(this)[LectureTeachersSideViewModel::class.java]
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
        binding.lectureSideViewModel = lectureTeachersSideViewModel
        binding.seriedThumbnail.load(HomePageTeachersSide.thumbnail.toString())
//        binding.floatingButtonUploadLecture.setOnClickListener {
//            findNavController().navigate(R.id.upload_lectures)
//        }
        lifecycleScope.launch {
            lectureTeachersSideViewModel.getLectures(HomePageTeachersSide.seriesid!!.toInt())
            lectureTeachersSideViewModel.result.observe(viewLifecycleOwner,
                {
                    when (it) {
                        is com.example.unacademy.Repository.Response.Success -> {
                            binding.shimmerFrameLayoutLectureSide.stopShimmerAnimation()
                            binding.shimmerFrameLayoutLectureSide.visibility=View.GONE
                            layoutManager = LinearLayoutManager(container?.context)
                            binding.recyclerViewLectureSide.layoutManager = layoutManager
                            adapter = RecyclerAdapterLectureTeachersSide(it.data)
                            binding.recyclerViewLectureSide.adapter = adapter
                            adapter.onClickListeer(object : RecyclerAdapterTeachersSideHomePage.ClickListener {
                                override fun OnClick(position: Int) {
                                       videoUrl=adapter.getLectureModelItem?.get(position)?.video.toString()
                                    val intent=Intent(
                                        activity,
                                        ExoPlayer::class.java
                                    )
                                    startActivity(intent)
                                }
                            })
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