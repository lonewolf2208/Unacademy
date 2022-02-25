package com.example.unacademy.Adapter.StudentSideAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentCardViewHomePageStudentsSideBinding
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.viewmodel.viewmodelStudentside.HomePageStudentSideViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RecyclerAdapterStudentWishlist(val context: Context, var getStudentSeries:ArrayList<getStudentSeriesItem>): RecyclerView.Adapter<RecyclerAdapterStudentWishlist.ViewHolder>(){
    var clickListener: ClickListener?=null
    var wishListFlag=true

    fun onClickListener( clickListener: ClickListener)
    {
        this.clickListener=clickListener

    }
    inner class ViewHolder(var binding: FragmentCardViewHomePageStudentsSideBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageView10.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val cardViewLecturesBinding: FragmentCardViewHomePageStudentsSideBinding = DataBindingUtil.inflate(layoutInflater,
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

//
            MainScope().launch {
                HomePageStudentSideViewModel().DeleteStudentWishlist(getStudentSeries!![position].id.toInt())
                getStudentSeries.removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(context, "Series removed from Wishlist", Toast.LENGTH_LONG).show()
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