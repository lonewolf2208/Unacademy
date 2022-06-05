package com.example.unacademy.Adapter.StudentSideAdapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentCardViewResultAnalysisStudentSideBinding
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.Question
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.QuizResultModelItem

class RecyclerAdapterQuestions(var studentSideGetQuizModelItem: List<Question>?):RecyclerView.Adapter<RecyclerAdapterQuestions.ViewHolder>() {
    inner class ViewHolder(var binding: FragmentCardViewResultAnalysisStudentSideBinding):
    RecyclerView.ViewHolder(binding.root) {

}


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterQuestions.ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val cardViewLecturesBinding: FragmentCardViewResultAnalysisStudentSideBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_card_view_result_analysis_student_side,parent,false)
        return ViewHolder(cardViewLecturesBinding)
    }

    override fun onBindViewHolder(
        holder: RecyclerAdapterQuestions.ViewHolder,
        position: Int
    ) {
        var attemptedAnswer= studentSideGetQuizModelItem!![position].answer

            when (attemptedAnswer) {
                1 -> {
                    holder.binding.Option1QuizResultPageAnalysis.setBackgroundColor(Color.parseColor("#1D9200"))
                }
                2 -> {

                    holder.binding.Option2QuizResultPageAnalysis.setBackgroundColor(Color.parseColor("#1D9200"))
                }
                3 -> {

                    holder.binding.Option3QuizResultPageAnalysis.setBackgroundColor(Color.parseColor("#1D9200"))
                }
                4 -> {

                    holder.binding.Option4QuizResultPageAnalysis.setBackgroundColor(Color.parseColor("#1D9200"))
                }
            }


        holder.binding.ResultAnalysisQuestionShow.text =
            studentSideGetQuizModelItem!![position].question.toString()
        holder.binding.Option1QuizResultPageAnalysis.text =
            studentSideGetQuizModelItem!![position].option1.toString()
        holder.binding.Option2QuizResultPageAnalysis.text =
            studentSideGetQuizModelItem!![position].option2.toString()
        holder.binding.Option3QuizResultPageAnalysis.text =
            studentSideGetQuizModelItem!![position].option3.toString()
        holder.binding.Option4QuizResultPageAnalysis.text =
            studentSideGetQuizModelItem!![position].option4.toString()
    }
    override fun getItemCount(): Int {
        return studentSideGetQuizModelItem!!.size
    }
}