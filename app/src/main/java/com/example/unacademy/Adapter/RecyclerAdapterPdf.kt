package com.example.unacademy.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterAttemptedQuizesStudentSide
import com.example.unacademy.R
import com.example.unacademy.databinding.CardViewPdfBinding
import com.example.unacademy.databinding.FragmentCardViewHomePageTeachersSideBinding
import com.example.unacademy.databinding.FragmentCardViewResultAnalysisStudentSideBinding
import com.example.unacademy.models.TeachersSideModels.UploadPdf.uploadpdf
import com.example.unacademy.models.TeachersSideModels.UploadPdf.uploadpdfItem
import com.example.unacademy.models.TeachersSideModels.getLectureModelItem

class RecyclerAdapterPdf(var pdgitem: uploadpdf?): RecyclerView.Adapter<RecyclerAdapterPdf.ViewHolder>()  {
    companion object
    {
        var pdfurl:String=""
    }
    var clickListener:ClickListener?=null

    fun onClickListeer( clickListener:ClickListener)
    {
        this.clickListener=clickListener
    }

    inner class ViewHolder(val binding: CardViewPdfBinding) :RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val cardViewLecturesBinding:CardViewPdfBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.card_view_pdf,parent,false)
        return ViewHolder(cardViewLecturesBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding.shimmerFrameLayoutHomePage.stopShimmerAnimation()
//        holder.binding.shimmerFrameLayoutHomePage.visibility=View.GONE
      holder.binding.textView124.text= pdgitem!![position].title.toString()
        holder.binding.imageView20.setOnClickListener {
            pdfurl = pdgitem!![position].doc.toString()
        }

    }

    override fun getItemCount(): Int {
        return pdgitem!!.size
    }

    interface ClickListener{
        fun OnClick(position:Int)
    }

}
