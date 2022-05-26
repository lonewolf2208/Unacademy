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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.Adapter.RecyclerAdapterLectureTeachersSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterAttemptedQuizesStudentSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterStudentWishlist
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.databinding.FragmentProfileStudentSideBinding
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.getStudentProfileModel.getStudentProfileModel
import com.example.unacademy.viewmodel.viewmodelStudentside.StudentProfileViewModel
import kotlinx.coroutines.launch

class ProfileStudentSide : Fragment() {

    companion object
    {
        var studentProfile:getStudentProfileModel?=null
    }
    lateinit var binding:FragmentProfileStudentSideBinding
    lateinit var profileSTudentSideViewModel: StudentProfileViewModel
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: RecyclerAdapterStudentWishlist
    lateinit var adapterGetQuiz:RecyclerAdapterAttemptedQuizesStudentSide
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        profileSTudentSideViewModel= ViewModelProvider(this)[StudentProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_profile_student_side, container, false)
        binding.wishlistViewModel=profileSTudentSideViewModel
        binding.shimmerFrameLayoutLatestSeriesProfilePageStudentSide.startShimmerAnimation()
        binding.ViewProfileStudent.setOnClickListener {
            findNavController().navigate(R.id.action_profileStudentSide_to_editYourProfileStudentSide)
        }
        binding.lifecycleOwner=this
        var attemptedQuiz=ArrayList<StudentSideGetQuizModelItem>()
        for(i in 0..(homePageStudentSide.totalQuiz.size-1))
        {
            if(homePageStudentSide.totalQuiz[i].is_attempted==true)
            {
                attemptedQuiz.add(homePageStudentSide.totalQuiz[i])
            }
        }
        lifecycleScope.launch {
            var result = profileSTudentSideViewModel.getWishlistSeries()
            result.observe(viewLifecycleOwner,
                {
                    when (it) {
                        is Response.Success -> {
                            var shimmerFrameLayoutHomePageQuiz=binding.shimmerFrameLayoutLatestSeriesProfilePageStudentSide
                            shimmerFrameLayoutHomePageQuiz?.stopShimmerAnimation()
                            shimmerFrameLayoutHomePageQuiz?.visibility=View.INVISIBLE
                            if(it.data!!.isEmpty())
                            {
                                binding.EmptyWishlistStudentSide.text="Your Wishlist is Empty"
                            }
                            layoutManager = LinearLayoutManager(
                                container?.context,LinearLayoutManager.HORIZONTAL,false)
                            binding.RecyclerAdapterWishlistStudentSide.layoutManager = layoutManager
                            adapter = RecyclerAdapterStudentWishlist(requireContext(), it.data!!)
                            binding.RecyclerAdapterWishlistStudentSide.adapter = adapter
                            adapter.notifyDataSetChanged()
                            adapter.onClickListener(object : RecyclerAdapterStudentWishlist.ClickListener {
                                override fun OnClick(position: Int) {
                                    HomePageTeachersSide.seriesid = adapter.getStudentSeries?.get(position)?.id!!.toInt()
                                    RecyclerAdapterLectureTeachersSide.series_name=adapter.getStudentSeries?.get(position)?.name.toString()
                                    RecyclerAdapterLectureTeachersSide.seriesDescription=adapter.getStudentSeries?.get(position)?.description.toString()
                                    RecyclerAdapterLectureTeachersSide.seriesThumbnail=adapter.getStudentSeries?.get(position)?.icon.toString()
                                    findNavController().navigate(R.id.lecturesTeachersSide2)
                                }
                            })
                        }
                        is Response.Error->Toast.makeText(requireContext(),it.errorMessage.toString(),Toast.LENGTH_LONG).show()
                    }
                }) }
        if(attemptedQuiz.isEmpty())
        {
            binding.EmptyAttemptedQuiz.visibility=View.VISIBLE
        }
        layoutManager= LinearLayoutManager(container?.context,LinearLayoutManager.HORIZONTAL, false)
        binding.RecyclerAdapterAttemptedQuizes.layoutManager=layoutManager
        adapterGetQuiz= RecyclerAdapterAttemptedQuizesStudentSide(attemptedQuiz as ArrayList<StudentSideGetQuizModelItem>)
        binding.RecyclerAdapterAttemptedQuizes.adapter=adapterGetQuiz
        adapterGetQuiz.onClickListener(object : RecyclerAdapterAttemptedQuizesStudentSide.ClickListener {
            override fun OnClick(position: Int) {
                homePageStudentSide.quizTitle = homePageStudentSide.totalQuiz!!?.get(position)?.title.toString()
                homePageStudentSide.quizDescription = homePageStudentSide.totalQuiz!![position].description.toString()
                homePageStudentSide.quizLectureCount = homePageStudentSide.totalQuiz!![position].questions.toString()
                homePageStudentSide.quizid = homePageStudentSide.totalQuiz!![position].id.toInt()
                homePageStudentSide.quizDuration = homePageStudentSide.totalQuiz!![position].duration
                findNavController().navigate(R.id.action_profileStudentSide_to_quizResultPage)
            }
        })
        lifecycleScope.launch {
            var result=profileSTudentSideViewModel.getProfile()
            result.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                        is Response.Success->
                        {
                            studentProfile= it.data
                        }
                        is Response.Error->
                        {
                            Toast.makeText(requireContext(),it.errorMessage.toString(),Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }
        return binding.root
    }
}