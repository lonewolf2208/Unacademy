package com.example.unacademy.Ui.StudentsSide

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.Activities.StudentStoryActivity
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterLatestSeries
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterOurEducatorsStudentSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterQuizTEachersSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterStudentStory
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.databinding.FragmentHomePageStudentSideBinding
import com.example.unacademy.models.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.viewmodel.viewmodelStudentside.HomePageStudentSideViewModel
import kotlinx.coroutines.launch

class homePageStudentSide : Fragment() {

    lateinit var binding:FragmentHomePageStudentSideBinding
    lateinit var homePageStudentSideViewModel:HomePageStudentSideViewModel
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: RecyclerAdapterLatestSeries
    lateinit var storiesAdapter:RecyclerAdapterStudentStory
    lateinit var adapterOurEducators:RecyclerAdapterOurEducatorsStudentSide
    lateinit var adapterGetQuiz:RecyclerAdapterQuizTEachersSide
    companion object
    {
        var studentStoryId:Int=0
        var educatorId:Int=0
        var quizid=0
        var quizTitle:String=""
        var quizDescription:String=""
        var quizLectureCount:String=""
        var totalQuiz:List<StudentSideGetQuizModelItem>?=null
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

        binding.homePageStudentSideModel=homePageStudentSideViewModel
        lifecycleScope.launch {
        var resultStudentStoryProfile=homePageStudentSideViewModel.getStudentStoryProfile()
            resultStudentStoryProfile.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                        is Response.Success->
                        {

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
                            adapter.wishListClickListener(object : RecyclerAdapterLatestSeries.ClickListener {
                                override fun OnClick(position: Int) {

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
                            adapterOurEducators.onClickListener(object : RecyclerAdapterOurEducatorsStudentSide.ClickListener {
                                override fun OnClick(position: Int) {
                                    educatorId= it.data?.get(position)!!.id
                                    lifecycleScope.launch {
                                     var result=   homePageStudentSideViewModel.addFollowing()
                                        result.observe(viewLifecycleOwner,
                                            {
                                                when(it) {
                                                    is Response.Success -> {
                                                        view?.findViewById<Button>(R.id.FollowButton)?.text="Following"
                                                    }
                                                    is Response.Error -> {
                                                        Toast.makeText(
                                                            context,
                                                            it.errorMessage.toString(),
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    }
                                                }
                                            })
                                    }
                                }
                            })

                        }
                        is Response.Error->Toast.makeText(context,it.errorMessage.toString(),Toast.LENGTH_LONG).show()
                    }
                })



            var GetQuizresult=homePageStudentSideViewModel.getQuiz()
            GetQuizresult.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                        is Response.Success->
                        {

                           totalQuiz=it.data
                            layoutManager= LinearLayoutManager(container?.context,LinearLayoutManager.HORIZONTAL, true)
                            binding.RecyclerAdapterDailQuizStudentSide.layoutManager=layoutManager
                            adapterGetQuiz= RecyclerAdapterQuizTEachersSide(it.data)
                            binding.RecyclerAdapterDailQuizStudentSide.adapter=adapterGetQuiz
                            adapterGetQuiz.onClickListener(object : RecyclerAdapterQuizTEachersSide.ClickListener {
                                override fun OnClick(position: Int) {
                                    quizTitle= it.data!!?.get(position).title.toString()
                                    quizDescription=it.data[position].description.toString()
                                    quizLectureCount=it.data[position].questions.toString()
                                    quizid=it.data[position].id.toInt()
                                    findNavController().navigate(R.id.action_homePageStudentSide_to_quizShowPageStudentSide)
                                }
                            })
                        }
                        is Response.Error->Toast.makeText(context,it.errorMessage.toString(),Toast.LENGTH_LONG).show()
                    }
                })
        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
    }
}