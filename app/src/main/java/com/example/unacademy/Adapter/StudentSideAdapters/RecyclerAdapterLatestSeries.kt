package com.example.unacademy.Adapter.StudentSideAdapters

import android.content.Context
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

class RecyclerAdapterLatestSeries(val context:Context,var getStudentSeries: List<getStudentSeriesItem>?): RecyclerView.Adapter<RecyclerAdapterLatestSeries.ViewHolder>(){
    var clickListener: ClickListener?=null
    var wishListFlag=true
    fun onClickListener( clickListener: ClickListener)
    {
        this.clickListener=clickListener

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
        holder.binding.SeriesNameStudentSide.text= getStudentSeries?.get(position)?.name.toString()
        if(getStudentSeries?.get(position)?.is_wishlisted==true)
        {
            holder.binding.WishListStudentSide.setBackgroundResource(R.drawable.ic_wishlist_student_side_selected)
        }
        holder.binding.WishListStudentSide.setOnClickListener {
            if(getStudentSeries?.get(position)?.is_wishlisted==true)
            {
               wishListFlag=true
            }
            if(getStudentSeries?.get(position)?.is_wishlisted==false)
            {
                wishListFlag=false
            }
            if(wishListFlag==false) {
                holder.binding.WishListStudentSide.setBackgroundResource(R.drawable.ic_wishlist_student_side_selected)
                MainScope().launch {
                        HomePageStudentSideViewModel().StudentWishlist(getStudentSeries!![position].id.toInt())
                   Toast.makeText(context,"Series added to wishlist",Toast.LENGTH_LONG).show()
                }
                wishListFlag = true
            }
            else
            {
                MainScope().launch {
                    HomePageStudentSideViewModel().DeleteStudentWishlist(getStudentSeries!![position].id.toInt())
                    Toast.makeText(context,"Series removed from Wishlist",Toast.LENGTH_LONG).show()
                }
                holder.binding.WishListStudentSide.setBackgroundResource(R.drawable.ic_wishlist_student_side_deselected)
                wishListFlag=false
            }
        }
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