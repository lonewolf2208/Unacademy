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
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.databinding.FragmentWishListStudentSideBinding
import com.example.unacademy.viewmodel.viewmodelStudentside.StudentProfileViewModel
import kotlinx.coroutines.launch


class WishListStudentSide : Fragment() {
lateinit var bindiing:FragmentWishListStudentSideBinding
lateinit var wishListSTudentSideViewModel:StudentProfileViewModel
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: RecyclerAdapterLatestSeries
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       wishListSTudentSideViewModel=ViewModelProvider(this)[StudentProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindiing =DataBindingUtil.inflate(inflater,R.layout.fragment_wish_list_student_side, container, false)
        bindiing.lifecycleOwner=this
        bindiing.wishlistViewModel=wishListSTudentSideViewModel
        lifecycleScope.launch {
            var result = wishListSTudentSideViewModel.getWishlistSeries()
            result.observe(viewLifecycleOwner,
                {
                    when (it) {
                        is Response.Success -> {
                            layoutManager = LinearLayoutManager(
                                container?.context)
                            bindiing.RecyclerViewWishlistedSeries.layoutManager = layoutManager
                            adapter = RecyclerAdapterLatestSeries(requireContext(), it.data!!)
                            bindiing.RecyclerViewWishlistedSeries.adapter = adapter
                            adapter.notifyDataSetChanged()
                            adapter.onClickListener(object : RecyclerAdapterLatestSeries.ClickListener {
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
        return bindiing.root
    }

}