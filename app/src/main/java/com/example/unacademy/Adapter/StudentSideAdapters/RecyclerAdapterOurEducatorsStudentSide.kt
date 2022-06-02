package com.example.unacademy.Adapter.StudentSideAdapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.Ui.StudentsSide.homePageStudentSide
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
    var clickListener:ClickListener?=null

    fun onClickListener( clickListener:ClickListener)
    {
        this.clickListener=clickListener
    }
    inner class ViewHolder(var binding:CardViewOurEducatorsStudentSideBinding):RecyclerView.ViewHolder(binding.root) {
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
        val cardViewLecturesBinding:CardViewOurEducatorsStudentSideBinding= DataBindingUtil.inflate(layoutInflater,
            R.layout.card_view_our_educators_student_side,parent,false)
        return ViewHolder(cardViewLecturesBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.EducatorNameStudentSide.text= educationDetails?.get(position)?.name.toString()
        holder.binding.educatorImageStudentSide.load(educationDetails?.get(position)?.picture.toString())
        if(educationDetails?.get(position)?.is_followed==true)
        {
            homePageStudentSide.is_following=true
//            holder.binding.FollowButton.setBackgroundResource(R.drawable.follow_button_border)
            holder.binding.FollowButton.strokeColor= ColorStateList.valueOf(Color.parseColor("#0E97B5"))
            holder.binding.FollowButton.strokeWidth=5
            holder.binding.FollowButton.setBackgroundColor(Color.WHITE)
            holder.binding.FollowButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#0E97B5")))
            holder.binding.FollowButton.text="Following"
        }
        holder.binding.FollowButton.setOnClickListener {
            educatorId= educationDetails?.get(position)?.id!!.toInt()

            if(is_following==false)
            {
//                holder.binding.FollowButton.setBackgroundResource(R.drawable.follow_button_border)
                homePageStudentSide.is_following=true
                holder.binding.FollowButton.text="Following"
                holder.binding.FollowButton.strokeColor= ColorStateList.valueOf(Color.parseColor("#0E97B5"))
                holder.binding.FollowButton.strokeWidth=5
                holder.binding.FollowButton.setBackgroundColor(Color.WHITE)
                holder.binding.FollowButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#0E97B5")))
                MainScope().launch {
                    HomePageStudentSideViewModel().addFollowing()
                }
                is_following=true
            }
            else
            {
                homePageStudentSide.is_following=false
                holder.binding.FollowButton.text="Follow"
                holder.binding.FollowButton.setBackgroundColor(Color.parseColor("#0E97B5"))
                holder.binding.FollowButton.setTextColor(ColorStateList.valueOf(Color.WHITE))
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