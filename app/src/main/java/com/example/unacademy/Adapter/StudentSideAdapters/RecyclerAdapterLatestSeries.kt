package com.example.unacademy.Adapter.StudentSideAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentCardViewHomePageStudentsSideBinding
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem

class RecyclerAdapterLatestSeries(var getStudentSeries: List<getStudentSeriesItem>?): RecyclerView.Adapter<RecyclerAdapterLatestSeries.ViewHolder>(){
    var clickListener: ClickListener?=null

    fun onClickListener( clickListener: ClickListener)
    {
        this.clickListener=clickListener
    }
    inner class ViewHolder(var binding: FragmentCardViewHomePageStudentsSideBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val cardViewLecturesBinding:FragmentCardViewHomePageStudentsSideBinding= DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_card_view_home_page_students_side,parent,false)
        return ViewHolder(cardViewLecturesBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.SeriesNameStudentSide.text= getStudentSeries?.get(position)?.name.toString()
        holder.binding.SeriesDescriptionStudentSide.text= getStudentSeries?.get(position)?.description.toString()
        holder.binding.imageView10.load(getStudentSeries?.get(position)?.icon.toString())
    }

    override fun getItemCount(): Int {
        return getStudentSeries!!.size
    }
    interface ClickListener{
        fun OnClick(position:Int)
    }
}