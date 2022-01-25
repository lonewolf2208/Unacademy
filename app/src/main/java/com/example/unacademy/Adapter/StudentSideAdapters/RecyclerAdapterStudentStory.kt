package com.example.unacademy.Adapter.StudentSideAdapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.Repository.StudentSideRepo.StudentStoryProfileRepo
import com.example.unacademy.databinding.CardViewStoriesStudentSideBinding
import com.example.unacademy.models.StudentStory.studentStoryModelItem

class RecyclerAdapterStudentStory(var studentStoryModelItem: List<studentStoryModelItem>?): RecyclerView.Adapter<RecyclerAdapterStudentStory.ViewHolder>() {
    var clickListener:ClickListener?=null
    var StudentStoryPicture=StudentStoryProfileRepo.studetStoryDataPicture
    fun onClickListener( clickListener:ClickListener)
    {
        this.clickListener=clickListener
    }
    inner class ViewHolder(var binding:CardViewStoriesStudentSideBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
        }
    }
    interface ClickListener{
        fun OnClick(position:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val cardViewLecturesBinding:CardViewStoriesStudentSideBinding= DataBindingUtil.inflate(layoutInflater,
            R.layout.card_view_stories_student_side,parent,false)
        return ViewHolder(cardViewLecturesBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.binding.storiStudentSide.load(StudentStoryPicture[position].toString())
    }

    override fun getItemCount(): Int {
        Log.d("Sisdasdwad", StudentStoryProfileRepo.studentStoryDataName.size.toString())
        return StudentStoryProfileRepo.studentStoryDataName.size
    }

}