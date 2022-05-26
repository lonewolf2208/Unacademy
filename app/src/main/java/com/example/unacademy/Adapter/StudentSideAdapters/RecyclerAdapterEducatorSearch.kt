package com.example.unacademy.Adapter.StudentSideAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.CardViewDailyQuizStudentSideBinding
import com.example.unacademy.databinding.CardViewEducatorSearchBinding
import com.example.unacademy.databinding.FragmentCardViewHomePageStudentsSideBinding
import com.example.unacademy.models.StudentSideModel.SearchStudentSide.SearchStudentSideItem
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem

class RecyclerAdapterEducatorSearch(var educatorProfileInfo: List<SearchStudentSideItem>?): RecyclerView.Adapter<RecyclerAdapterEducatorSearch.ViewHolder>() {
    var clickListener:ClickListener?=null
    interface ClickListener{
        fun OnClick(position:Int)
    }
    fun onClickListener( clickListener: ClickListener)
    {
        this.clickListener=clickListener
    }

    inner class ViewHolder(var binding: CardViewEducatorSearchBinding):RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val cardViewLecturesBinding: CardViewEducatorSearchBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.card_view_educator_search,parent,false)
        return ViewHolder(cardViewLecturesBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.EducatorName.text= educatorProfileInfo!![position].name
//
    }

    override fun getItemCount(): Int {
        return educatorProfileInfo!!.size
    }
}