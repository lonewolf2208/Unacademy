package com.example.unacademy.Ui.StudentsSide

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
import com.example.unacademy.Activities.LectureActivity
import com.example.unacademy.Activities.StudentStoryActivity
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterLatestSeries
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterOurEducatorsStudentSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterStudentStory
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.databinding.FragmentHomePageStudentSideBinding
import com.example.unacademy.viewmodel.viewmodelStudentside.HomePageStudentSideViewModel
import kotlinx.coroutines.launch

class homePageStudentSide : Fragment() {

    lateinit var binding:FragmentHomePageStudentSideBinding
    lateinit var homePageStudentSideViewModel:HomePageStudentSideViewModel
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: RecyclerAdapterLatestSeries
    lateinit var storiesAdapter:RecyclerAdapterStudentStory
    lateinit var adapterOurEducators:RecyclerAdapterOurEducatorsStudentSide

    companion object
    {
        var studentStoryId:Int=0
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageStudentSideViewModel=ViewModelProvider(this)[HomePageStudentSideViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home_page_student_side, container, false)
        binding.lifecycleOwner=this
        Toast.makeText(context,findNavController().previousBackStackEntry?.destination?.label.toString(),Toast.LENGTH_LONG).show()
        binding.homePageStudentSideModel=homePageStudentSideViewModel
        lifecycleScope.launch {
        var resultStudentStoryProfile=homePageStudentSideViewModel.getStudentStoryProfile()
            resultStudentStoryProfile.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                        is Response.Success->
                        {
                            Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()
                            layoutManager= LinearLayoutManager(container?.context,LinearLayoutManager.HORIZONTAL, true)
                            binding.recyclerViewStoriesStudentSide.layoutManager=layoutManager
                            storiesAdapter= RecyclerAdapterStudentStory(it.data)
                            binding.recyclerViewStoriesStudentSide.adapter=storiesAdapter
                            storiesAdapter.onClickListener(object : RecyclerAdapterStudentStory.ClickListener {
                                override fun OnClick(position: Int) {
                                    studentStoryId=
                                        storiesAdapter.studentStoryModelItem?.get(position)!!.educator
                                    val intent=Intent(activity,StudentStoryActivity::class.java)
                                    startActivity(intent)
                                }
                            })
                        }
                        is Response.Error->
                        {
                            Toast.makeText(context,it.errorMessage.toString(),Toast.LENGTH_LONG).show()
                        }
                    }
                })
            var result=homePageStudentSideViewModel.getSeries()
            result.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                        is Response.Success->
                        {
                            layoutManager= LinearLayoutManager(container?.context,LinearLayoutManager.HORIZONTAL, true)
                            binding.recyclerViewLatestSeriesStudentSide.layoutManager=layoutManager
                            adapter= RecyclerAdapterLatestSeries(it.data)
                            binding.recyclerViewLatestSeriesStudentSide.adapter=adapter
                            adapter.onClickListener(object : RecyclerAdapterLatestSeries.ClickListener {
                                override fun OnClick(position: Int) {
                                    HomePageTeachersSide.seriesid = adapter.getStudentSeries?.get(position)?.id!!.toInt()
                                    HomePageTeachersSide.thumbnail =adapter.getStudentSeries?.get(position)?.icon!!.toString()
                                   findNavController().navigate(R.id.action_homePageStudentSide_to_lecturesTeachersSide2)
                                }
                            })
                        }
                    }
                })
            var oureducators=homePageStudentSideViewModel.getEducators()
            oureducators.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                        is Response.Success->
                        {
                            layoutManager= LinearLayoutManager(container?.context,LinearLayoutManager.HORIZONTAL, true)
                            binding.OurEducatorsRecyclerViewStudentSide.layoutManager=layoutManager
                            adapterOurEducators= RecyclerAdapterOurEducatorsStudentSide(it.data)
                            binding.OurEducatorsRecyclerViewStudentSide.adapter=adapterOurEducators
                        }
                        is Response.Error->Toast.makeText(context,it.errorMessage.toString(),Toast.LENGTH_LONG).show()
                    }
                })
        }
        return binding.root
    }


}