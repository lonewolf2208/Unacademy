package com.example.unacademy.Adapter.StudentSideAdapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentCardViewResultAnalysisStudentSideBinding
import com.example.unacademy.models.StudentSideModel.QuizResultRepo.QuizResultModelItem

class RecyclerAdapterResultAnalysisStudentSide(var studentSideGetQuizModelItem: List<QuizResultModelItem>?): RecyclerView.Adapter<RecyclerAdapterResultAnalysisStudentSide.ViewHolder>() {
    inner class ViewHolder(var binding:FragmentCardViewResultAnalysisStudentSideBinding):RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterResultAnalysisStudentSide.ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val cardViewLecturesBinding:FragmentCardViewResultAnalysisStudentSideBinding= DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_card_view_result_analysis_student_side,parent,false)
        return ViewHolder(cardViewLecturesBinding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(
        holder: RecyclerAdapterResultAnalysisStudentSide.ViewHolder,
        position: Int
    ) {
        var attemptedAnswer= studentSideGetQuizModelItem!![position].attempted_answer
        var correctedAnswer=studentSideGetQuizModelItem!![position].correct_answer
        if(attemptedAnswer!=correctedAnswer)
        {
            when(attemptedAnswer)
            {
                1-> {
                    holder.binding.Option1QuizResultPageAnalysis.isChecked = true
                    holder.binding.Option1QuizResultPageAnalysis.setBackgroundColor(Color.parseColor("#CD0000"))
                }
                2-> {
                    holder.binding.Option2QuizResultPageAnalysis.isChecked = true
                    holder.binding.Option2QuizResultPageAnalysis.setBackgroundColor(Color.parseColor("#CD0000"))
                }
                3-> {
                    holder.binding.Option3QuizResultPageAnalysis.isChecked = true
                    holder.binding.Option3QuizResultPageAnalysis.setBackgroundColor(Color.parseColor("#CD0000"))
                }
                4-> {
                    holder.binding.Option4QuizResultPageAnalysis.isChecked = true
                    holder.binding.Option4QuizResultPageAnalysis.setBackgroundColor(Color.parseColor("#CD0000"))
                }
            }
            when (correctedAnswer) {
                1 -> {
                    holder.binding.Option1QuizResultPageAnalysis.setBackgroundColor(Color.GREEN)
                }
                2 -> {

                    holder.binding.Option2QuizResultPageAnalysis.setBackgroundColor(Color.GREEN)
                }
                3 -> {

                    holder.binding.Option3QuizResultPageAnalysis.setBackgroundColor(Color.GREEN)
                }
                4 -> {

                    holder.binding.Option4QuizResultPageAnalysis.setBackgroundColor(Color.GREEN)
                }
            }
        }
        else {
            when (attemptedAnswer) {
                1 -> {
                    holder.binding.Option1QuizResultPageAnalysis.isChecked = true
                    holder.binding.Option1QuizResultPageAnalysis.setBackgroundColor(Color.GREEN)
                }
                2 -> {
                    holder.binding.Option2QuizResultPageAnalysis.isChecked = true
                    holder.binding.Option2QuizResultPageAnalysis.setBackgroundColor(Color.GREEN)
                }
                3 -> {
                    holder.binding.Option3QuizResultPageAnalysis.isChecked = true
                    holder.binding.Option3QuizResultPageAnalysis.setBackgroundColor(Color.GREEN)
                }
                4 -> {
                    holder.binding.Option4QuizResultPageAnalysis.isChecked = true
                    holder.binding.Option4QuizResultPageAnalysis.setBackgroundColor(Color.GREEN)
                }
            }
        }

        holder.binding.ResultAnalysisQuestionShow.text =
            studentSideGetQuizModelItem!![position].question.question.toString()
        holder.binding.Option1QuizResultPageAnalysis.text =
            studentSideGetQuizModelItem!![position].question.option1.toString()
        holder.binding.Option2QuizResultPageAnalysis.text =
            studentSideGetQuizModelItem!![position].question.option2.toString()
        holder.binding.Option3QuizResultPageAnalysis.text =
            studentSideGetQuizModelItem!![position].question.option3.toString()
        holder.binding.Option4QuizResultPageAnalysis.text =
            studentSideGetQuizModelItem!![position].question.option4.toString()
        holder.binding.Option1QuizResultPageAnalysis

    }
    override fun getItemCount(): Int {
       return studentSideGetQuizModelItem!!.size
    }
}