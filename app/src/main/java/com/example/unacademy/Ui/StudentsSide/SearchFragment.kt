package com.example.unacademy.Ui.StudentsSide

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.unacademy.Adapter.StudentSideAdapters.SearchToggleAdapter
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentSearchBinding
import com.example.unacademy.models.StudentSideModel.SearchStudentSide.SearchStudentSideItem
import com.example.unacademy.models.StudentSideModel.getSearchCourseModel.SearchCourseModelItem
import com.example.unacademy.viewmodel.viewmodelStudentside.HomePageStudentSideViewModel
import com.example.unacademy.viewmodel.viewmodelStudentside.SearchStudentSIdeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    companion object
    {
        var searchProfileData=MutableLiveData<Response<List<SearchStudentSideItem>?>>()
        var searchCourseData=MutableLiveData<Response<List<SearchCourseModelItem>?>>()
    }

    lateinit var searchPageStudentSideViewModel: SearchStudentSIdeViewModel
    lateinit var binding:FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchPageStudentSideViewModel=ViewModelProvider(this)[SearchStudentSIdeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)
       binding.SearchContainer.addTextChangedListener(
           object : TextWatcher {
               override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

               }

               override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                   Toast.makeText(requireContext(),"Called",Toast.LENGTH_LONG).show()
//                   Toast.makeText(requireContext(),binding.SearchContainer.text.toString(),Toast.LENGTH_LONG).show()

               }

               override fun afterTextChanged(p0: Editable?) {
                   lifecycleScope.launch {
//                        Toast.makeText(requireContext(),binding.SearchContainer.text.toString(),Toast.LENGTH_LONG).show()
                       if (!binding.SearchContainer.text.isEmpty()) {
                           var searchProfile =
                               searchPageStudentSideViewModel.getProfile(binding.SearchContainer.text.toString())
                           searchProfile.observe(
                               viewLifecycleOwner
                           ) {

                               when (it) {
                                   is Response.Loading -> binding.progressBarSearch.visibility =
                                       View.VISIBLE
                                   is Response.Success -> {
                                       binding.progressBarSearch.visibility = View.INVISIBLE
//                                       Toast.makeText(requireContext(),it.data.toString(),Toast.LENGTH_LONG).show()
                                       searchProfileData.postValue(Response.Success(it.data))
                                   }
                                   is Response.Error -> Toast.makeText(
                                       requireContext(),
                                       it.errorMessage.toString(),
                                       Toast.LENGTH_LONG
                                   ).show()
                               }
                           }

                           var searchCourse =
                               searchPageStudentSideViewModel.getCourse(binding.SearchContainer.text.toString())
                           searchCourse.observe(
                               viewLifecycleOwner
                           ) {
                               when (it) {
                                   is Response.Loading -> binding.progressBarSearch.visibility =
                                       View.VISIBLE
                                   is Response.Success -> {
                                       binding.progressBarSearch.visibility = View.INVISIBLE
//                                       Toast.makeText(requireContext(),it.data.toString(),Toast.LENGTH_LONG).show()
                                       searchCourseData.postValue(Response.Success(it.data))
                                   }
                                   is Response.Error -> Toast.makeText(
                                       requireContext(),
                                       it.errorMessage.toString(),
                                       Toast.LENGTH_LONG
                                   ).show()
                               }
                           }

                       }
                   }
               }
           }
       )
        binding.viewPagerSearch.adapter=SearchToggleAdapter(this@SearchFragment)
        TabLayoutMediator(binding.tabLayout2,binding.viewPagerSearch) { tab, position ->
            when (position) {
                0 -> tab.text = "Educators"
                1 -> tab.text = "Courses"
            }
        }.attach()
        return binding.root
    }


}