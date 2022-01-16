package com.example.unacademy.Ui.TeachersSide

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.Activities.NavBarActivity
import com.example.unacademy.Adapter.RecyclerAdapterTeachersSideHomePage
import com.example.unacademy.R
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.databinding.FragmentHomePageTeachersSideBinding
import com.example.unacademy.viewModel.HomePageViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class HomePageTeachersSide : Fragment() {

    lateinit var binding :FragmentHomePageTeachersSideBinding
    lateinit var homePageViewModel:HomePageViewModel
    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterTeachersSideHomePage.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageViewModel=ViewModelProvider(this)[HomePageViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_page_teachers_side,
            container,
            false
        )

        binding.lifecycleOwner=this
        binding.homePageViewModel=homePageViewModel
        lifecycleScope.launch {
            homePageViewModel.getSeries()
        }

       homePageViewModel.result.observe(viewLifecycleOwner,
           {
               when(it)
               {
                   is com.example.unacademy.Repository.Response.Success -> {
                       layoutManager=LinearLayoutManager(container?.context)
                       binding.recyclerViewHomePage.layoutManager=layoutManager
                       adapter=RecyclerAdapterTeachersSideHomePage(it.data)
                       binding.recyclerViewHomePage.adapter=adapter
                   }

                   is com.example.unacademy.Repository.Response.Error -> Toast.makeText(
                       context,
                       it.errorMessage.toString(),
                       Toast.LENGTH_LONG
                   ).show()
                   is com.example.unacademy.Repository.Response.Loading -> Toast.makeText(
                       context,
                       "Loading",
                       Toast.LENGTH_LONG
                   ).show()
               }

           })


        return binding.root
    }
}