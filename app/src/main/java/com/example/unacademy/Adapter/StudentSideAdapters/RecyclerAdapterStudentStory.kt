package com.example.unacademy.Adapter.StudentSideAdapters

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
    var name=ArrayList<String>()
    var size =0
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

        var flag=0
        for(i in name)
        {
            if(studentStoryModelItem!![position].name == i)
            {
                flag=1
                break
            }
        }
        if(flag==0)
        {
            name.add(studentStoryModelItem!![position].name.toString())
            size++
            holder.binding.storiStudentSide.load(studentStoryModelItem!![position].picture.toString())
        }
    }

    override fun getItemCount(): Int {
        return StudentStoryProfileRepo.studentStoryData.size
    }

}