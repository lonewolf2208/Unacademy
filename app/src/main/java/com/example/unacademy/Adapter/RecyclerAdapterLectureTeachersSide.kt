package com.example.unacademy.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.Repository.TeachersSideRepo.getLectureRepo
import com.example.unacademy.databinding.FragmentCardViewHomePageTeachersSideBinding
import com.example.unacademy.databinding.FragmentCardViewLecturesBinding
import com.example.unacademy.models.TeachersSideModels.getLectureModelItem

class RecyclerAdapterLectureTeachersSide(val getLectureModelItem: List<getLectureModelItem>?): RecyclerView.Adapter<RecyclerAdapterLectureTeachersSide.ViewHolder>()  {
    var clickListener: RecyclerAdapterTeachersSideHomePage.ClickListener?=null

    fun onClickListeer( clickListener: RecyclerAdapterTeachersSideHomePage.ClickListener)
    {
        this.clickListener=clickListener
    }
    inner class ViewHolder(val binding:FragmentCardViewLecturesBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val cardViewLecturesBinding:FragmentCardViewLecturesBinding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_card_view_lectures,parent,false)
        return ViewHolder(cardViewLecturesBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.binding.LectureName.text= getLectureModelItem!![position].name
//        holder.binding.imageView11.load(getLectureModelItem[position].video)

    }

    override fun getItemCount(): Int {
       return getLectureModelItem!!.size
    }


}