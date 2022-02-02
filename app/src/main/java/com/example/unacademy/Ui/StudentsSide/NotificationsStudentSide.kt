package com.example.unacademy.Ui.StudentsSide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterLatestSeries
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterStudentNotifications
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterStudentStory
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentNotificationsStudentSideBinding
import com.example.unacademy.viewmodel.viewmodelStudentside.StudentNotificationsVewModel
import kotlinx.coroutines.launch

class NotificationsStudentSide : Fragment() {

    lateinit var binding:FragmentNotificationsStudentSideBinding
    lateinit var studentNotificationsVewModel: StudentNotificationsVewModel
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: RecyclerAdapterStudentNotifications
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      studentNotificationsVewModel=ViewModelProvider(this)[StudentNotificationsVewModel()::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_notifications_student_side, container, false)
        binding.lifecycleOwner=this
        binding.getNotificationViewmodel=studentNotificationsVewModel
        lifecycleScope.launch {
            var result=studentNotificationsVewModel.getStudentNotification()
            result.observe(viewLifecycleOwner,
                {
                    when(it)
                    {
                        is Response.Success->
                        {
                            layoutManager= LinearLayoutManager(container?.context)
                            binding.RecyclerViewNotificationsStudentSide.layoutManager=layoutManager
                            adapter= RecyclerAdapterStudentNotifications(it.data)
                            binding.RecyclerViewNotificationsStudentSide.adapter=adapter
                        }
                        is Response.Error->
                        {
                            Toast.makeText(context,"ERRRRR",Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }
        return binding.root
    }

}