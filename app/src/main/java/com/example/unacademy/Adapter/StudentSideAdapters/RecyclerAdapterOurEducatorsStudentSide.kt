package com.example.unacademy.Adapter.StudentSideAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.CardViewOurEducatorsStudentSideBinding
import com.example.unacademy.databinding.FragmentCardViewHomePageStudentsSideBinding
import com.example.unacademy.models.StudentSideModel.getStudentSeries.EducatorDetails

class RecyclerAdapterOurEducatorsStudentSide(var educationDetails: List<EducatorDetails>?): RecyclerView.Adapter<RecyclerAdapterOurEducatorsStudentSide.ViewHolder>()
{
    var clickListener:ClickListener?=null

    fun onClickListener( clickListener:ClickListener)
    {
        this.clickListener=clickListener
    }
    inner class ViewHolder(var binding:CardViewOurEducatorsStudentSideBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.FollowButton.setOnClickListener {
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
        holder.binding.courseNameOurEducators.text= educationDetails?.get(position)?.bio.toString()
    }

    override fun getItemCount(): Int {
      return educationDetails!!.size
    }
}