package com.example.unacademy.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentCardViewHomePageTeachersSideBinding
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem
import com.example.unacademy.viewModel.HomePageViewModel

class RecyclerAdapterTeachersSideHomePage(val educatorSeriesModelItem: List<educatorSeriesModelItem>?):RecyclerView.Adapter<RecyclerAdapterTeachersSideHomePage.ViewHolder>() {
    inner class ViewHolder(val binding:FragmentCardViewHomePageTeachersSideBinding) :RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val layoutInflater=LayoutInflater.from(parent.context)
        val cardViewHomePageTeachersSideBinding:FragmentCardViewHomePageTeachersSideBinding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_card_view_home_page_teachers_side,parent,false)
        return ViewHolder(cardViewHomePageTeachersSideBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.batchNameHomeTeacherSide.text= educatorSeriesModelItem?.get(position)?.name.toString()
        holder.binding.descriptionhomepageteachersSide.text= educatorSeriesModelItem?.get(position)?.description.toString()
        holder.binding.totalectureshomepageteachersSide.text=
            educatorSeriesModelItem?.get(position)?.educator.toString()
        holder.binding.batchimageteachersside.load(educatorSeriesModelItem?.get(position)?.icon)
    }

    override fun getItemCount(): Int {
        return educatorSeriesModelItem!!.size
    }


}