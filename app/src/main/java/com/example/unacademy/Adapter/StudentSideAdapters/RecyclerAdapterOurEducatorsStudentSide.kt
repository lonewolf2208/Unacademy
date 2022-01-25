package com.example.unacademy.Adapter.StudentSideAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.CardViewOurEducatorsStudentSideBinding
import com.example.unacademy.databinding.FragmentCardViewHomePageStudentsSideBinding
import com.example.unacademy.models.StudentSideModel.getStudentSeries.EducatorDetails
import com.example.unacademy.viewmodel.viewmodelStudentside.HomePageStudentSideViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RecyclerAdapterOurEducatorsStudentSide(val context:Context,var educationDetails: List<EducatorDetails>?): RecyclerView.Adapter<RecyclerAdapterOurEducatorsStudentSide.ViewHolder>()
{
    companion object {
        var educatorId = 0
    }
    var is_following=false
//    var clickListener:ClickListener?=null
//
//    fun onClickListener( clickListener:ClickListener)
//    {
//        this.clickListener=clickListener
//    }
    inner class ViewHolder(var binding:CardViewOurEducatorsStudentSideBinding):RecyclerView.ViewHolder(binding.root) {
//        init {
//            binding.FollowButton.setOnClickListener {
//                clickListener?.OnClick(adapterPosition)
//            }
//        }
    }
    interface ClickListener{
        fun OnClick(position:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val cardViewLecturesBinding:CardViewOurEducatorsStudentSideBinding= DataBindingUtil.inflate(layoutInflater,
            R.layout.card_view_our_educators_student_side,parent,false)
        return ViewHolder(cardViewLecturesBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.EducatorNameStudentSide.text= educationDetails?.get(position)?.name.toString()
        holder.binding.educatorImageStudentSide.load(educationDetails?.get(position)?.picture.toString())
        if(educationDetails?.get(position)?.is_followed==true)
        {
            holder.binding.FollowButton.text="Following"
        }
        holder.binding.FollowButton.setOnClickListener {
            educatorId= educationDetails?.get(position)?.id!!.toInt()
            if(educationDetails?.get(position)?.is_followed==true)
            {
                is_following=true
            }
            if(educationDetails?.get(position)?.is_followed==false)
            {
                is_following=false
            }
            if(is_following==false) {
                holder.binding.FollowButton.text="Following"
                MainScope().launch {
                    HomePageStudentSideViewModel().addFollowing()
                }
                is_following=true
            }
            else
            {
                holder.binding.FollowButton.text="Follow"
                MainScope().launch {
                    HomePageStudentSideViewModel().teacherUnfollowing()
                }
                is_following=false
            }
        }
        holder.binding.courseNameOurEducators.text= educationDetails?.get(position)?.bio.toString()
    }

    override fun getItemCount(): Int {
      return educationDetails!!.size
    }
}