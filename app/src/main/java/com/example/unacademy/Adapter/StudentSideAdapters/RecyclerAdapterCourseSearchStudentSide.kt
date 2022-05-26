package com.example.unacademy.Adapter.StudentSideAdapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.CardViewEducatorSearchBinding
import com.example.unacademy.databinding.FragmentCardViewHomePageTeachersSideBinding
import com.example.unacademy.models.StudentSideModel.getSearchCourseModel.SearchCourseModelItem

class RecyclerAdapterCourseSearchStudentSide(var courseSearch:List<SearchCourseModelItem>?):RecyclerView.Adapter<RecyclerAdapterCourseSearchStudentSide.ViewHolder>() {
    var clickListener:ClickListener?=null
    interface ClickListener{
        fun OnClick(position:Int)
    }
    fun onClickListener( clickListener:ClickListener)
    {
        this.clickListener=clickListener
    }

    inner class ViewHolder(var binding:FragmentCardViewHomePageTeachersSideBinding):RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterCourseSearchStudentSide.ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val cardViewHomePageTeachersSideBinding: FragmentCardViewHomePageTeachersSideBinding =
            DataBindingUtil.inflate(layoutInflater,
                R.layout.fragment_card_view_home_page_teachers_side,parent,false)
        return ViewHolder(cardViewHomePageTeachersSideBinding)
    }

    override fun onBindViewHolder(
        holder: RecyclerAdapterCourseSearchStudentSide.ViewHolder,
        position: Int
    ) {
        holder.binding.batchNameHomeTeacherSide.text= courseSearch!![position].name.toString()
//        holder.binding.descriptionhomepageteachersSide.text= courseSearch?.get(position)?.description.toString()
//        holder.binding.totalectureshomepageteachersSide.text=
//            courseSearch?.get(position)?.lectures.toString()
//        holder.binding.batchimageteachersside.load(courseSearch?.get(position)?.icon)
//        holder.binding.batchimageteachersside
    }

    override fun getItemCount(): Int {
        Log.d("courseSearch", courseSearch!!.size.toString())
        return courseSearch!!.size
    }
}