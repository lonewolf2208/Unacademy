package com.example.unacademy.Ui.TeachersSide

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import com.example.unacademy.R
import com.example.unacademy.databinding.FragmentMakeAnnouncementBinding


class MakeAnnouncement : Fragment() {

    lateinit var binding:FragmentMakeAnnouncementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_make_announcement, container, false)

       binding.MakeAnnouncement.setOnClickListener {

           var builder =
               NotificationCompat.Builder(requireContext().applicationContext, "Unacademy")
                   .setSmallIcon(R.drawable.ic_bgauth)
                   .setContentTitle(binding.AnnouncemnetSubject.text)
                   .setContentText(binding.AnnouncementMessage.text)
                   .setPriority(NotificationCompat.PRIORITY_DEFAULT)
          var managerCompat=NotificationManagerCompat.from(requireContext())
           managerCompat.notify(1,builder.build())

       }
           return binding.root
       }
    }


