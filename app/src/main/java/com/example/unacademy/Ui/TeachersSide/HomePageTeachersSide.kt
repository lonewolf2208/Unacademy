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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
       var teachersInfo:getTeachersProfileModel?=null
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

        lifecycleScope.launch {
            homePageViewModel.getSeries()
        }

       homePageViewModel.result.observe(viewLifecycleOwner,
           {
               when(it)
               {
                   is Response.Success -> {
                       if(it.data!!.isEmpty())
                       {
                           binding.EmptySeries.text="Upload Series !! "
                       }
                       var shimmerFrameLayoutHomePage=view?.findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayoutHomePage)
                       shimmerFrameLayoutHomePage?.stopShimmerAnimation()
                       shimmerFrameLayoutHomePage?.visibility=View.GONE
                       layoutManager=LinearLayoutManager(container?.context)
                       binding.recyclerViewHomePage.layoutManager=layoutManager
                       adapter=RecyclerAdapterTeachersSideHomePage(it.data)
                       binding.recyclerViewHomePage.adapter=adapter
                       adapter.onClickListeer(object : RecyclerAdapterTeachersSideHomePage.ClickListener {
                           override fun OnClick(position: Int) {
                             seriesid = adapter.educatorSeriesModelItem?.get(position)?.id!!.toInt()
                               RecyclerAdapterLectureTeachersSide.series_name=adapter.educatorSeriesModelItem?.get(position)?.name.toString()
                               RecyclerAdapterLectureTeachersSide.seriesDescription=adapter.educatorSeriesModelItem?.get(position)?.description.toString()
                               RecyclerAdapterLectureTeachersSide.seriesThumbnail=adapter.educatorSeriesModelItem?.get(position)?.icon.toString()
                               val intent=Intent(activity,LectureActivity()::class.java)
                               startActivity(intent)
                           }
                       })
                   }
                   is Response.TokenExpire->
                   {
                       Toast.makeText(context,"Token Expired",Toast.LENGTH_LONG).show()
                       suspend fun GetToken() {
//                           var job=lifecycleScope.async {
//                               getNewToken(RetrofitClient.init()).getToken()
//                           }
//                           job.join()
                           homePageViewModel.getSeries()
                           Log.w("JKFJLASFJKLAFJ","Home Page::::::: "+Splash_Screen.readInfo("access").toString())
                       }
                       lifecycleScope.launch {
                           GetToken()
                       }

                   }

                   is Response.Error -> {
                       Toast.makeText(
                           context,
                           it.errorMessage.toString(),
                           Toast.LENGTH_LONG
                       ).show()

//
////                           var tpken= Splash_Screen.readInfo("access")
//
//                           Toast.makeText(context,tpken.toString(),Toast.LENGTH_LONG).show()

                   }
                   is com.example.unacademy.Repository.Response.Loading -> Toast.makeText(
                       context,
                       "Loading",
                       Toast.LENGTH_LONG
                   ).show()
               }

           })
        lifecycleScope.launch {
            var AccessToken = Splash_Screen.readInfo("access").toString()
           var token = AccessToken
            RetrofitClient.init().getTeachersProfile("Bearer ${token}").enqueue(object :
                Callback<getTeachersProfileModel?> {
                override fun onResponse(
                    call: Call<getTeachersProfileModel?>,
                    response: retrofit2.Response<getTeachersProfileModel?>
                ) {
                    if(response.isSuccessful)
                    {
                     teachersInfo=response.body()
                    }
                    else
                    {
                        Toast.makeText(context,response.message().toString(),Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<getTeachersProfileModel?>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }


        return binding.root
    }
}