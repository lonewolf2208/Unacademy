package com.example.unacademy.Ui.StudentsSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.Adapter.RecyclerAdapterLectureTeachersSide
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterLatestSeries
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterStudentWishlist
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.databinding.FragmentProfileStudentSideBinding
import com.example.unacademy.viewmodel.viewmodelStudentside.StudentProfileViewModel
import kotlinx.coroutines.launch

class ProfileStudentSide : Fragment() {

    lateinit var binding:FragmentProfileStudentSideBinding
    lateinit var profileSTudentSideViewModel: StudentProfileViewModel
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: RecyclerAdapterStudentWishlist
    override fun onCreate(savedInstanceState: Bundle?) {
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
        binding.lifecycleOwner=this
        lifecycleScope.launch {
            var result = profileSTudentSideViewModel.getWishlistSeries()
            result.observe(viewLifecycleOwner,
                {
                    when (it) {
                        is Response.Success -> {
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
                            if(it.data!!.isEmpty())
                            {
                                binding.EmptyWishlistStudentSide.text="Your Wishlist is Empty"
                            }
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
                    }


                })
        }
        return binding.root
    }


}