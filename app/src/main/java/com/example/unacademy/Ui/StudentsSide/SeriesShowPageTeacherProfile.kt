package com.example.unacademy.Ui.StudentsSide

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.Activities.LectureActivity
import com.example.unacademy.Adapter.RecyclerAdapterLectureTeachersSide
import com.example.unacademy.Adapter.RecyclerAdapterTeachersSideHomePage
import com.example.unacademy.R
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.databinding.FragmentSeriesShowPageTeacherProfileBinding

class SeriesShowPageTeacherProfile : Fragment() {
    lateinit var binding:FragmentSeriesShowPageTeacherProfileBinding
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter:RecyclerAdapterTeachersSideHomePage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,
            R.layout.fragment_series_show_page_teacher_profile,
            container,
            false
        )
        layoutManager= LinearLayoutManager(container?.context)
        binding.RecyclerView.layoutManager=layoutManager
        adapter= RecyclerAdapterTeachersSideHomePage(FragmentTeachersProfileShowPageStudentSide.teacher_profile_series)
        if(FragmentTeachersProfileShowPageStudentSide.teacher_profile_series?.size==0)
        {
            binding.NoSeries.visibility=View.VISIBLE
        }
        binding.RecyclerView.adapter=adapter
        adapter.onClickListeer(object : RecyclerAdapterTeachersSideHomePage.ClickListener {
            override fun OnClick(position: Int) {
                HomePageTeachersSide.seriesid = adapter.educatorSeriesModelItem?.get(position)?.id!!.toInt()
                RecyclerAdapterLectureTeachersSide.series_name=adapter.educatorSeriesModelItem?.get(position)?.name.toString()
                RecyclerAdapterLectureTeachersSide.seriesDescription=adapter.educatorSeriesModelItem?.get(position)?.description.toString()
                RecyclerAdapterLectureTeachersSide.seriesThumbnail=adapter.educatorSeriesModelItem?.get(position)?.icon.toString()
                val intent= Intent(activity, LectureActivity()::class.java)
                startActivity(intent)
            }
        })
        return binding.root
    }


}