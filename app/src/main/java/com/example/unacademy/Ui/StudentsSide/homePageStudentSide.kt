package com.example.unacademy.Ui.StudentsSide

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.unacademy.Activities.StudentStoryActivity
import com.example.unacademy.Adapter.RecyclerAdapterLectureTeachersSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterLatestSeries
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterOurEducatorsStudentSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterQuizTEachersSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterStudentStory
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.StudentSideRepo.GetQuizRepoStudentSide
import com.example.unacademy.Repository.StudentSideRepo.StudentStoryProfileRepo
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentHomePageStudentSideBinding
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.viewmodel.viewmodelStudentside.HomePageStudentSideViewModel
import kotlinx.coroutines.launch

class homePageStudentSide : Fragment(),View.OnClickListener {

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
        var quizid=0
        var quizTitle:String=""
        var quizDescription:String=""
        var quizLectureCount:String=""
        var quizDuration=0
        var totalQuiz=ArrayList<StudentSideGetQuizModelItem>()
        var teacher_id:Int=0
        var series=ArrayList<getStudentSeriesItem>()
        var is_following=false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageStudentSideViewModel=ViewModelProvider(this)[HomePageStudentSideViewModel()::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home_page_student_side, container, false)
        binding.lifecycleOwner=this
        binding.shimmerFrameLayoutLatestSeriesHomePageTeachersSide.startShimmerAnimation()
        binding.shimmerFrameLayoutOurEducatorsHomePageTeachersSide.startShimmerAnimation()
        binding.shimmerFrameLayoutQuizHomePageTeachersSide.startShimmerAnimation()
        binding.shimmerFrameLayoutStoryHomePageTeachersSide.startShimmerAnimation() 
        binding.homePageStudentSideModel=homePageStudentSideViewModel
        binding.SeeMoreDailyQuiz.setOnClickListener(this)

        lifecycleScope.launch {
            var resultStudentStoryProfile = homePageStudentSideViewModel.getStudentStoryProfile()
            resultStudentStoryProfile.observe(viewLifecycleOwner
            ) {
                when (it) {
                    is Response.Success -> {
                        var shimmerFrameLayoutHomePageQuiz =
                            binding.shimmerFrameLayoutStoryHomePageTeachersSide
                        shimmerFrameLayoutHomePageQuiz?.stopShimmerAnimation()
                        shimmerFrameLayoutHomePageQuiz?.visibility = View.INVISIBLE
                        layoutManager = LinearLayoutManager(
                            container?.context,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.recyclerViewStoriesStudentSide.layoutManager = layoutManager
                        storiesAdapter = RecyclerAdapterStudentStory(it.data)
                        binding.recyclerViewStoriesStudentSide.adapter = storiesAdapter
                        storiesAdapter.onClickListener(object :
                            RecyclerAdapterStudentStory.ClickListener {
                            override fun OnClick(position: Int) {
                                studentStoryId =
                                    StudentStoryProfileRepo.studentStoryId[position]
                                val intent = Intent(activity, StudentStoryActivity::class.java)
                                startActivity(intent)
                            }
                        })
                    }
                    is Response.Error -> {
                        Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            var result = homePageStudentSideViewModel.getSeries()
            result.observe(viewLifecycleOwner
            ) {
                when (it) {
                    is Response.Success -> {
                        series = it.data as ArrayList<getStudentSeriesItem>
                        var shimmerFrameLayoutHomePageQuiz =
                            binding.shimmerFrameLayoutLatestSeriesHomePageTeachersSide
                        shimmerFrameLayoutHomePageQuiz?.stopShimmerAnimation()
                        shimmerFrameLayoutHomePageQuiz?.visibility = View.INVISIBLE
                        var size = it.data?.size?.toInt()
                        var slideModel = ArrayList<SlideModel>()
                        for (i in 0..(size!!.toInt() - 1)) {
                            slideModel.add(SlideModel(it.data!![i].icon))
                        }
                        binding.recyclerViewLatestSeriesStudentSide.setImageList(slideModel)
                        binding.recyclerViewLatestSeriesStudentSide.setItemClickListener(object : ItemClickListener {
                            override fun onItemSelected(position: Int) {
                                HomePageTeachersSide.seriesid =
                                   it.data[position].id.toInt()
                                RecyclerAdapterLectureTeachersSide.series_name =
                                    it.data[position].name.toString()
                                RecyclerAdapterLectureTeachersSide.seriesDescription =
                                    it.data[position].description.toString()
                                RecyclerAdapterLectureTeachersSide.seriesThumbnail =
                                   it.data[position].icon.toString()
                                findNavController().navigate(R.id.action_homePageStudentSide_to_lecturesTeachersSide2)
                            }
                        })

                    }
                    is Response.TokenExpire -> {
                        Toast.makeText(requireContext(), "Token Exxpired", Toast.LENGTH_LONG)
                            .show()
                        getNewToken(RetrofitClient.init())
                        lifecycleScope.launch {
                            homePageStudentSideViewModel.getSeries()
                        }
                    }
                }
            }
            var oureducators = homePageStudentSideViewModel.getEducators()
            oureducators.observe(viewLifecycleOwner
            ) {
                when (it) {
                    is Response.Success -> {
                        var shimmerFrameLayoutHomePage =
                            binding.shimmerFrameLayoutOurEducatorsHomePageTeachersSide
                        shimmerFrameLayoutHomePage?.stopShimmerAnimation()
                        shimmerFrameLayoutHomePage?.visibility = View.INVISIBLE
                        layoutManager = LinearLayoutManager(
                            container?.context,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.OurEducatorsRecyclerViewStudentSide.layoutManager =
                            layoutManager
                        adapterOurEducators =
                            RecyclerAdapterOurEducatorsStudentSide(requireContext(), it.data)
                        binding.OurEducatorsRecyclerViewStudentSide.adapter =
                            adapterOurEducators
                        adapterOurEducators.onClickListener(object :
                            RecyclerAdapterOurEducatorsStudentSide.ClickListener {
                            override fun OnClick(position: Int) {
                                teacher_id = adapterOurEducators.educationDetails!![position].id
                                RecyclerAdapterOurEducatorsStudentSide.educatorId = teacher_id
                                findNavController().navigate(R.id.action_homePageStudentSide_to_fragmentTeachersProfileShowPageStudentSide)
                            }
                        })



                    }
                    is Response.Error -> {
                        Toast.makeText(
                            context,
                            it.errorMessage.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        lifecycleScope.launch {
            var getQuizresult=homePageStudentSideViewModel.getQuiz()
            getQuizresult.observe(viewLifecycleOwner
            ) {
                when (it) {
                    is Response.Success -> {
                        var shimmerFrameLayoutHomePageQuiz =
                            binding.shimmerFrameLayoutQuizHomePageTeachersSide
                        shimmerFrameLayoutHomePageQuiz?.stopShimmerAnimation()
                        shimmerFrameLayoutHomePageQuiz?.visibility = View.INVISIBLE
                        totalQuiz = GetQuizRepoStudentSide.studentQuizWithNoZeroQuestions
                        layoutManager = LinearLayoutManager(
                            container?.context,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.RecyclerAdapterDailQuizStudentSide.layoutManager = layoutManager
                        adapterGetQuiz =
                            RecyclerAdapterQuizTEachersSide(GetQuizRepoStudentSide.studentQuizWithNoZeroQuestions)
                        binding.RecyclerAdapterDailQuizStudentSide.adapter = adapterGetQuiz
                        adapterGetQuiz.onClickListener(object :
                            RecyclerAdapterQuizTEachersSide.ClickListener {
                            override fun OnClick(position: Int) {
//                                Toast.makeText(requireContext(), it.data!![position].is_attempted.toString(),Toast.LENGTH_LONG).show()
                                quizTitle = GetQuizRepoStudentSide.studentQuizWithNoZeroQuestions[position].title.toString()
                                quizDescription = GetQuizRepoStudentSide.studentQuizWithNoZeroQuestions[position].description.toString()
                                quizLectureCount =GetQuizRepoStudentSide.studentQuizWithNoZeroQuestions[position].questions.toString()
                                quizid = GetQuizRepoStudentSide.studentQuizWithNoZeroQuestions[position].id.toInt()
                                quizDuration = GetQuizRepoStudentSide.studentQuizWithNoZeroQuestions[position].duration
                                if (GetQuizRepoStudentSide.studentQuizWithNoZeroQuestions[position].is_attempted == true) {
                                    findNavController().navigate(R.id.action_homePageStudentSide_to_quizResultPage)
                                } else {
                                    findNavController().navigate(R.id.action_homePageStudentSide_to_quizShowPageStudentSide)
                                }
                            }
                        })
                    }
                    is Response.Error -> Toast.makeText(
                        context,
                        it.errorMessage.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            var result = homePageStudentSideViewModel.getSeries()
            result.observe(viewLifecycleOwner
            ) {
                when (it) {
                    is Response.Success -> {
                        series = it.data as ArrayList<getStudentSeriesItem>
                        var shimmerFrameLayoutHomePageQuiz =
                            binding.shimmerFrameLayoutLatestSeriesHomePageTeachersSide
                        shimmerFrameLayoutHomePageQuiz?.stopShimmerAnimation()
                        shimmerFrameLayoutHomePageQuiz?.visibility = View.INVISIBLE
                        layoutManager = LinearLayoutManager(
                            container?.context,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.RecyclerViewCourseForYou.layoutManager =
                            layoutManager
                        adapter = RecyclerAdapterLatestSeries(
                            requireContext(),
                            it.data as ArrayList<getStudentSeriesItem>
                        )
                        binding.RecyclerViewCourseForYou.adapter = adapter
                        adapter.onClickListener(object :
                            RecyclerAdapterLatestSeries.ClickListener {
                            override fun OnClick(position: Int) {
                                HomePageTeachersSide.seriesid =
                                    adapter.getStudentSeries?.get(position)?.id!!.toInt()
                                RecyclerAdapterLectureTeachersSide.series_name =
                                    adapter.getStudentSeries?.get(position)?.name.toString()
                                RecyclerAdapterLectureTeachersSide.seriesDescription =
                                    adapter.getStudentSeries?.get(position)?.description.toString()
                                RecyclerAdapterLectureTeachersSide.seriesThumbnail =
                                    adapter.getStudentSeries?.get(position)?.icon.toString()
                                findNavController().navigate(R.id.action_homePageStudentSide_to_lecturesTeachersSide2)
                            }
                        })
                    }
                    is Response.TokenExpire -> {
                        Toast.makeText(requireContext(), "Token Exxpired", Toast.LENGTH_LONG)
                            .show()
                        getNewToken(RetrofitClient.init())
                        lifecycleScope.launch {
                            homePageStudentSideViewModel.getSeries()
                        }
                    }
                }
            }

        }
        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.SeeMoreDailyQuiz->findNavController().navigate(R.id.action_homePageStudentSide_to_selfStudyStudentSide)
        }
    }


}