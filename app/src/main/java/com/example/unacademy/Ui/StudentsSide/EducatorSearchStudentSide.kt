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
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterEducatorSearch
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterLatestSeries
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterOurEducatorsStudentSide
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentEducatorSearchStudentSideBinding
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem


class EducatorSearchStudentSide : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: RecyclerAdapterEducatorSearch
    lateinit var binding:FragmentEducatorSearchStudentSideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_educator_search_student_side, container, false)
        SearchFragment.searchProfileData.observe(viewLifecycleOwner
        ) {
            when (it) {
//                is Response.Loading->binding.progressBarEducator.visibility=View.VISIBLE
                is Response.Success -> {
//                    binding.progressBarEducator.visibility=View.INVISIBLE
//                Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_LONG).show()
                    layoutManager = LinearLayoutManager(
                        container?.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    binding.RecyclerViewSearchEducator.layoutManager =
                        layoutManager
                    adapter = RecyclerAdapterEducatorSearch(it.data)
                    binding.RecyclerViewSearchEducator.adapter = adapter
                    adapter.onClickListener(object : RecyclerAdapterEducatorSearch.ClickListener {
                        override fun OnClick(position: Int) {
                            homePageStudentSide.teacher_id = adapter.educatorProfileInfo!![position].id
                            RecyclerAdapterOurEducatorsStudentSide.educatorId =
                                homePageStudentSide.teacher_id
                            findNavController().navigate(R.id.fragmentTeachersProfileShowPageStudentSide)
                        }
                    })

                }
            }
        }
        return binding.root
    }


}