package com.example.unacademy.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentCardViewHomePageTeachersSideBinding
import com.example.unacademy.models.TeachersSideModels.educatorSeries.educatorSeriesModelItem

class RecyclerAdapterTeachersSideHomePage(val educatorSeriesModelItem: List<educatorSeriesModelItem>?):RecyclerView.Adapter<RecyclerAdapterTeachersSideHomePage.ViewHolder>() {
    var clickListener:ClickListener?=null

    fun onClickListeer( clickListener:ClickListener)
    {
        this.clickListener=clickListener
    }

    inner class ViewHolder(val binding:FragmentCardViewHomePageTeachersSideBinding,listener: ClickListener) :RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val layoutInflater=LayoutInflater.from(parent.context)
        val cardViewHomePageTeachersSideBinding:FragmentCardViewHomePageTeachersSideBinding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_card_view_home_page_teachers_side,parent,false)
        return ViewHolder(cardViewHomePageTeachersSideBinding,clickListener!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding.shimmerFrameLayoutHomePage.stopShimmerAnimation()
//        holder.binding.shimmerFrameLayoutHomePage.visibility=View.GONE
        holder.binding.batchNameHomeTeacherSide.text= educatorSeriesModelItem?.get(position)?.name.toString()
        holder.binding.descriptionhomepageteachersSide.text= educatorSeriesModelItem?.get(position)?.description.toString()
        holder.binding.totalectureshomepageteachersSide.text=
            educatorSeriesModelItem?.get(position)?.lectures.toString()
        holder.binding.batchimageteachersside.load(educatorSeriesModelItem?.get(position)?.icon)
        holder.binding.batchimageteachersside

    }

    override fun getItemCount(): Int {
        return educatorSeriesModelItem!!.size
    }

     interface ClickListener{
        fun OnClick(position:Int)
    }

}