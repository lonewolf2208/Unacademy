package com.example.unacademy.Adapter.StudentSideAdapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentCardViewHomePageStudentsSideBinding
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.viewmodel.viewmodelStudentside.HomePageStudentSideViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.security.acl.Owner

class RecyclerAdapterLatestSeries(val context:Context,var getStudentSeries:ArrayList<getStudentSeriesItem>): RecyclerView.Adapter<RecyclerAdapterLatestSeries.ViewHolder>(){
    var clickListener: ClickListener?=null
    var wishListFlag=true

    fun onClickListener( clickListener: ClickListener)
    {
        this.clickListener=clickListener
    }
    interface ClickListener{
        fun OnClick(position:Int)
    }
    inner class ViewHolder(var binding: FragmentCardViewHomePageStudentsSideBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageView10.setOnClickListener {
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
        holder.binding.SeriesNameStudentSide.text= getStudentSeries[holder.adapterPosition].name.toString()
        if(getStudentSeries?.get(position)?.is_wishlisted==true)
        {
            holder.binding.WishListStudentSide.setBackgroundResource(R.drawable.ic_wishlist_student_side_selected)
        }
        Log.w("Size",position.toString())
        holder.binding.SeriesDescriptionStudentSide.text= getStudentSeries[holder.adapterPosition].description.toString()
        holder.binding.imageView10.load(getStudentSeries?.get(holder.adapterPosition)?.icon.toString())
        holder.binding.LectureCountStudentSide.text=getStudentSeries?.get(holder.adapterPosition)?.lectures.toString()
        holder.binding.WishListStudentSide.setOnClickListener {
            if(wishListFlag==false) {
                holder.binding.WishListStudentSide.setBackgroundResource(R.drawable.ic_wishlist_student_side_selected)
                MainScope().launch {
                        HomePageStudentSideViewModel().StudentWishlist(getStudentSeries!![position].id.toInt())
                }
                wishListFlag = true
            }
            else
            {
                holder.binding.WishListStudentSide.setBackgroundResource(R.drawable.ic_wishlist_student_side_deselected)
                MainScope().launch {
                    HomePageStudentSideViewModel().DeleteStudentWishlist(getStudentSeries!![position].id.toInt())
                }
                wishListFlag=false
            }
        }
    }

    override fun getItemCount(): Int {
        Log.w("Size",getStudentSeries.size.toString())
        return getStudentSeries!!.size
    }

}