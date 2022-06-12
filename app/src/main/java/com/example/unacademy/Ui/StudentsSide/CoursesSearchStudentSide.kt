package com.example.unacademy.Ui.StudentsSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.Adapter.RecyclerAdapterLectureTeachersSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterCourseSearchStudentSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterEducatorSearch
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.databinding.FragmentCardViewHomePageTeachersSideBinding
import com.example.unacademy.databinding.FragmentCoursesSearchStudentSideBinding


class CoursesSearchStudentSide : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: RecyclerAdapterCourseSearchStudentSide
    lateinit var binding:FragmentCoursesSearchStudentSideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_courses_search_student_side, container, false)
        layoutManager = LinearLayoutManager(
            container?.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.RecyclerViewCourseSearch.layoutManager =
            layoutManager
        SearchFragment.searchCourseData.observe(viewLifecycleOwner
        ) {
            when (it) {
//                is Response.Loading->binding.progressBarCourse.visibility=View.VISIBLE
                is Response.Success -> {
//                    binding.progressBarCourse.visibility=View.INVISIBLE
//                    Toast.makeText(requireContext(),it.data.toString(),Toast.LENGTH_LONG).show()
                    adapter = RecyclerAdapterCourseSearchStudentSide(it.data)
                    binding.RecyclerViewCourseSearch.adapter = adapter
                    adapter.onClickListener(object : RecyclerAdapterCourseSearchStudentSide.ClickListener {
                        override fun OnClick(position: Int) {
                            HomePageTeachersSide.seriesid =
                                adapter.courseSearch?.get(position)?.id?.toInt()
                            RecyclerAdapterLectureTeachersSide.series_name =
                                adapter.courseSearch?.get(position)?.name.toString()
                            RecyclerAdapterLectureTeachersSide.seriesDescription =
                                adapter.courseSearch?.get(position)?.description.toString()
                            RecyclerAdapterLectureTeachersSide.seriesThumbnail =
                                adapter.courseSearch?.get(position)?.icon.toString()
                            findNavController().navigate(R.id.lecturesTeachersSide2)
                        }
                    })
                }
            }
        }
        return binding.root
    }


}