package com.example.unacademy.Adapter.StudentSideAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentCardViewHomePageStudentsSideBinding
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem

class RecyclerAdapterLatestSeries(var getStudentSeries: List<getStudentSeriesItem>?): RecyclerView.Adapter<RecyclerAdapterLatestSeries.ViewHolder>(){
    var clickListener: ClickListener?=null
    var wishlistClickListenet:ClickListener?=null
    fun onClickListener( clickListener: ClickListener)
    {
        this.clickListener=clickListener

    }
    fun wishListClickListener(wishlistClickListener: ClickListener)
    {
        this.wishlistClickListenet=wishlistClickListener
    }
    inner class ViewHolder(var binding: FragmentCardViewHomePageStudentsSideBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageView10.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
            binding.WishListStudentSide.setOnClickListener {
                wishlistClickListenet?.OnClick(adapterPosition)
                binding.WishListStudentSide.setBackgroundResource(R.drawable.ic_wishlist_student_side_selected)
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
        holder.binding.LectureCountStudentSide.text=getStudentSeries?.get(position)?.lectures.toString()
    }

    override fun getItemCount(): Int {
        return getStudentSeries!!.size
    }
    interface ClickListener{
        fun OnClick(position:Int)
    }
}