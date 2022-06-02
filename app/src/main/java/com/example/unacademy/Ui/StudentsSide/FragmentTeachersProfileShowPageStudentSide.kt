package com.example.unacademy.Ui.StudentsSide

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.unacademy.Adapter.StudentSideAdapters.FragmentStateChangeAdapter
import com.example.unacademy.R
import com.example.unacademy.Repository.StudentSideRepo.TeacherProfileRepoStudentSide
import com.example.unacademy.databinding.FragmentTeachersProfileShowPageStudentSideBinding
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.models.StudentSideModel.teachersProfileModel.teacher_profile_student_side
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import com.example.unacademy.viewmodel.viewmodelStudentside.TeacherProfileShowStudentSideViewModel
import com.google.android.gms.common.api.Response
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class FragmentTeachersProfileShowPageStudentSide : Fragment(),View.OnClickListener{

    lateinit var binding:FragmentTeachersProfileShowPageStudentSideBinding
    lateinit var viewModel:TeacherProfileShowStudentSideViewModel
    var flag=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this)[TeacherProfileShowStudentSideViewModel::class.java]
    }
    companion object
    {
        var teacher_profile_series:List<getStudentSeriesItem>?=null
        var teacher_profile_quizzes:List<StudentSideGetQuizModelItem>?=null
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=DataBindingUtil.inflate(inflater,
            R.layout.fragment_teachers_profile_show_page_student_side,
            container,
            false
        )
        binding.shapeableImageView2.bringToFront()
            val dialodView =
                LayoutInflater.from(requireContext()).inflate(R.layout.lottie_file_loader, null)

            val mBuilder = AlertDialog.Builder(requireContext())
                .setView(dialodView)
            val alertDialog: AlertDialog = mBuilder.create()
            alertDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            alertDialog.show()
        binding.lifecycleOwner=this
        binding.teacherProfileModel=viewModel
        binding.FollowBUttonTeachersProfile.setOnClickListener(this)
        lifecycleScope.launch {
            var result=viewModel.getProfile(homePageStudentSide.teacher_id)
            result.observe(viewLifecycleOwner
            ) {
                when (it) {
                    is com.example.unacademy.Repository.Response.Success -> {
                        alertDialog.dismiss()
                        if(homePageStudentSide.is_following==true)
                        {
                            flag=0
                            binding.FollowBUttonTeachersProfile.text = "Following"
                            binding.FollowBUttonTeachersProfile.strokeColor= ColorStateList.valueOf(Color.parseColor("#0E97B5"))
                            binding.FollowBUttonTeachersProfile.strokeWidth=5
                            binding.FollowBUttonTeachersProfile.setBackgroundColor(Color.WHITE)
                            binding.FollowBUttonTeachersProfile.setTextColor(ColorStateList.valueOf(Color.parseColor("#0E97B5")))
                        }
                        binding.shapeableImageView2.load(it.data?.picture)
                        binding.TeacherProfileShowPageFollowerCount.text =
                            it.data!!.followers.toString()
                        binding.TeacherProfileShowPageSeriesCount.text =
                            it.data!!.educator_series.size.toString()
                        binding.QuizzesShowPageTeacherProfileShowPage.text =
                            it.data.educator_quiz.size.toString()
                        binding.TeacherNameTeacherProfile.text = it.data.name
                        binding.TeacherDescriptionTeacherProfileShowPage.text = it.data.qual
                        teacher_profile_series = it.data.educator_series
                        teacher_profile_quizzes = it.data.educator_quiz
                    }
                }
            }

        }
        binding.viewPager.adapter=FragmentStateChangeAdapter(this@FragmentTeachersProfileShowPageStudentSide)
        TabLayoutMediator(binding.tabLayout,binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Quizzes"
                1 -> tab.text = "Series"
            }
        }.attach()

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.FollowBUttonTeachersProfile ->
            {
                lifecycleScope.launch {
                    if (flag % 2 == 0) {
                        var result = viewModel.addFollowing()
                        result.observe(viewLifecycleOwner
                        ) {
                            when (it) {
                                is com.example.unacademy.Repository.Response.Success -> {
                                    binding.FollowBUttonTeachersProfile.text = "Following"
                                    binding.FollowBUttonTeachersProfile.strokeColor= ColorStateList.valueOf(Color.parseColor("#0E97B5"))
                                    binding.FollowBUttonTeachersProfile.strokeWidth=5
                                    binding.FollowBUttonTeachersProfile.setBackgroundColor(Color.WHITE)
                                    binding.FollowBUttonTeachersProfile.setTextColor(ColorStateList.valueOf(Color.parseColor("#0E97B5")))
                                    flag++
                                }
                            }
                        }
                    }
                    else
                    {
                        var result=viewModel.teacherUnfollowing()
                        result.observe(viewLifecycleOwner
                        ) {
                            when (it) {
                                is com.example.unacademy.Repository.Response.Success -> {
                                    flag++
                                    binding.FollowBUttonTeachersProfile.text="Follow"
                                    binding.FollowBUttonTeachersProfile.setBackgroundColor(Color.parseColor("#0E97B5"))
                                    binding.FollowBUttonTeachersProfile.setTextColor(ColorStateList.valueOf(Color.WHITE))
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}