package com.example.unacademy.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentCardViewHomePageTeachersSideBinding
import com.example.unacademy.viewModel.HomePageViewModel

class RecyclerAdapterTeachersSideHomePage():RecyclerView.Adapter<RecyclerAdapterTeachersSideHomePage.ViewHolder>() {
    inner class ViewHolder(val binding:FragmentCardViewHomePageTeachersSideBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind( homePageViewModel: HomePageViewModel)
        {
            binding.homePageViewModel=homePageViewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val layoutInflater=LayoutInflater.from(parent.context)
        val cardViewHomePageTeachersSideBinding:FragmentCardViewHomePageTeachersSideBinding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_card_view_home_page_teachers_side,parent,false)
        return ViewHolder(cardViewHomePageTeachersSideBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}