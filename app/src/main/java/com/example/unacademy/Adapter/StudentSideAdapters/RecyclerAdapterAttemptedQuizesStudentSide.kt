package com.example.unacademy.Adapter.StudentSideAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.R
import com.example.unacademy.databinding.CardViewDailyQuizStudentSideBinding
import com.example.unacademy.models.StudentSideModel.StudentSideGetQuiz.StudentSideGetQuizModelItem

class RecyclerAdapterAttemptedQuizesStudentSide(var studentSideGetQuizModelItem: ArrayList<StudentSideGetQuizModelItem>): RecyclerView.Adapter<RecyclerAdapterAttemptedQuizesStudentSide.ViewHolder>() {
    var attemptedQuiz=ArrayList<StudentSideGetQuizModelItem>()
    var clickListener:ClickListener?=null
    interface ClickListener{
        fun OnClick(position:Int)
    }
    fun onClickListener( clickListener: RecyclerAdapterAttemptedQuizesStudentSide.ClickListener)
    {
        this.clickListener=clickListener
    }
    inner class ViewHolder(var binding: CardViewDailyQuizStudentSideBinding):RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterAttemptedQuizesStudentSide.ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val cardViewLecturesBinding:CardViewDailyQuizStudentSideBinding= DataBindingUtil.inflate(layoutInflater,
            R.layout.card_view_daily_quiz_student_side,parent,false)
        return ViewHolder(cardViewLecturesBinding)
    }


    override fun onBindViewHolder(
        holder: RecyclerAdapterAttemptedQuizesStudentSide.ViewHolder,
        position: Int
    ) {
        if(studentSideGetQuizModelItem!![position].is_attempted==true) {
            holder.binding.bookmarkAttempted.visibility = View.VISIBLE
            holder.binding.QuizDescription.text =
                studentSideGetQuizModelItem?.get(position)?.description.toString()
            holder.binding.QuizTitle.text =
                studentSideGetQuizModelItem?.get(position)!!.title.toString()
            holder.binding.QuestionCountQuiz.text =
                studentSideGetQuizModelItem!![position].questions.toString()
        }
    }

    override fun getItemCount(): Int {
        return studentSideGetQuizModelItem!!.size
    }

}