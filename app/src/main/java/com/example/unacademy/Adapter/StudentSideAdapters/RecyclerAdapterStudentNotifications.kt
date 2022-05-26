package com.example.unacademy.Adapter.StudentSideAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.R
import com.example.unacademy.databinding.CardviewStudentNotificationsStudentSideBinding
import com.example.unacademy.models.StudentSideModel.StudentNotifications.StudentNotificationsModelItem

class RecyclerAdapterStudentNotifications(var studentSideGetQuizModelItem: List<StudentNotificationsModelItem>?): RecyclerView.Adapter<RecyclerAdapterStudentNotifications.ViewHolder>() {
    inner class ViewHolder(var binding: CardviewStudentNotificationsStudentSideBinding):RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterStudentNotifications.ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val cardViewLecturesBinding:CardviewStudentNotificationsStudentSideBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.cardview_student_notifications_student_side,parent,false)
        return ViewHolder(cardViewLecturesBinding)
    }

    override fun onBindViewHolder(
        holder: RecyclerAdapterStudentNotifications.ViewHolder,
        position: Int
    ) {

        holder.binding.TitleNotificationStudentSide.text= studentSideGetQuizModelItem!![position].subject.toString()
        holder.binding.BodyNotificationStudentSide.text= studentSideGetQuizModelItem!![position].message.toString()
    }

    override fun getItemCount(): Int {
       return studentSideGetQuizModelItem!!.size
    }
}