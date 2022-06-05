package com.example.unacademy.Ui

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.unacademy.Adapter.RecyclerAdapterPdf
import com.example.unacademy.R
import com.example.unacademy.Repository.TeachersSideRepo.getPdfRepo
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentPdfShowPageBinding
import com.example.unacademy.models.TeachersSideModels.UploadPdf.uploadpdf
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PdfShowPage : Fragment() {
    companion object{
        var url:String=""
        }
    var downloadId:Long=0
        lateinit var binding:FragmentPdfShowPageBinding
        private var layoutManager: RecyclerView.LayoutManager?=null
        lateinit var adapter: RecyclerAdapterPdf
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding =DataBindingUtil.inflate(layoutInflater ,R.layout.fragment_pdf_show_page, container, false)
        var token:String=""
        val job= lifecycleScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
            token = AccessToken
        }
       var result=getPdfRepo(RetrofitClient.init()).getPdfApi(HomePageTeachersSide.seriesid!!.toInt(),token.toString())
        result.observe(viewLifecycleOwner
        ) {
            when (it) {
                is com.example.unacademy.Repository.Response.Success -> {
                    if(it.data!!.isEmpty())
                    {
                        binding.EmptyNotes.visibility=View.VISIBLE
                    }
                    layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                    binding.recyclerviewPdf.layoutManager = layoutManager
                    adapter = RecyclerAdapterPdf(it.data as uploadpdf?)
                    binding.recyclerviewPdf.adapter = adapter
                    adapter.onClickListeer(object : RecyclerAdapterPdf.ClickListener {
                        override fun OnClick(position: Int) {
                         Toast.makeText(requireContext(),"Downloading...",Toast.LENGTH_LONG).show()
                            url= adapter.pdgitem!![position].doc.toString()
                            val request = DownloadManager.Request(url.toUri())
                            request.apply {
                                setTitle("Omega")
                                setDescription("Downloading...")
                                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                setMimeType("application/pdf")
                                setDestinationInExternalPublicDir(
                                    Environment.DIRECTORY_DOWNLOADS,
                                    "Educool Downloads/Notes/Omega.pdf"
                                )
                                setAllowedOverRoaming(true)
                                setAllowedOverMetered(true)
                            }
                            val downloadManager =
                                activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                            downloadId = downloadManager.enqueue(request)
                        }

                        }
                    )}

                }
            }
        return binding.root
    }


}