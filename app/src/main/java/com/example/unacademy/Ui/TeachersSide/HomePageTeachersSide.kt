package com.example.unacademy.Ui.TeachersSide

import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.unacademy.Activities.LectureActivity
import com.example.unacademy.Adapter.RecyclerAdapterLectureTeachersSide
import com.example.unacademy.Adapter.RecyclerAdapterTeachersSideHomePage
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.Repository.getNewToken
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.api.RetrofitClient
import com.example.unacademy.databinding.FragmentHomePageTeachersSideBinding
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.models.TeachersSideModels.getTeachersProfile.getTeachersProfileModel
import com.example.unacademy.viewmodel.HomePageViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback

class HomePageTeachersSide : Fragment() {

    companion object
    {
        var seriesid:Int?=null
        var latesSeries:List<getStudentSeriesItem>?=null
    }
    lateinit var binding :FragmentHomePageTeachersSideBinding
    lateinit var homePageViewModel:HomePageViewModel
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter:RecyclerAdapterTeachersSideHomePage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageViewModel=ViewModelProvider(this)[HomePageViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_page_teachers_side,
            container,
            false
        )

        binding.lifecycleOwner=this
        binding.shimmerFrameLayoutHomePage.startShimmerAnimation()
        lifecycleScope.launch {
            homePageViewModel.getSeries()
        }

       homePageViewModel.result.observe(viewLifecycleOwner
       ) {
           var result:ArrayList<getStudentSeriesItem>?=null
           when (it) {
               is Response.Success -> {
                   latesSeries=it.data
                   var shimmerFrameLayoutHomePage =
                       view?.findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayoutHomePage)

                   shimmerFrameLayoutHomePage?.stopShimmerAnimation()
                   shimmerFrameLayoutHomePage?.visibility = View.INVISIBLE
                   if (it.data!!.isEmpty()) {
                       binding.EmptySeries.text = "Upload Series !! "
                   }
                   layoutManager = LinearLayoutManager(container?.context ,LinearLayoutManager.HORIZONTAL,
                       false)
                   binding.recyclerViewHomePage.layoutManager = layoutManager
                   adapter = RecyclerAdapterTeachersSideHomePage(it.data)
                   binding.recyclerViewHomePage.adapter = adapter
                   adapter.onClickListeer(object :
                       RecyclerAdapterTeachersSideHomePage.ClickListener {
                       override fun OnClick(position: Int) {
                           seriesid = adapter.educatorSeriesModelItem?.get(position)?.id!!.toInt()
                           RecyclerAdapterLectureTeachersSide.series_name =
                               adapter.educatorSeriesModelItem?.get(position)?.name.toString()
                           RecyclerAdapterLectureTeachersSide.seriesDescription =
                               adapter.educatorSeriesModelItem?.get(position)?.description.toString()
                           RecyclerAdapterLectureTeachersSide.seriesThumbnail =
                               adapter.educatorSeriesModelItem?.get(position)?.icon.toString()
                           val intent = Intent(activity, LectureActivity()::class.java)
                           startActivity(intent)
                       }
                   })
               }

               is Response.Error -> {
                   Toast.makeText(
                       context,
                       "Error",
                       Toast.LENGTH_LONG
                   ).show()

               }
               is com.example.unacademy.Repository.Response.Loading -> Toast.makeText(
                   context,
                   "Loading",
                   Toast.LENGTH_LONG
               ).show()
           }
           binding.seeMoreSeries.setOnClickListener {
               findNavController().navigate(R.id.action_homePageTeachersSide_to_latestSeriesPageStudentSide2)
           }

       }

        return binding.root
    }
}