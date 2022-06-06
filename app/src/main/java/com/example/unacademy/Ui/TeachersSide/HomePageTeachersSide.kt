package com.example.unacademy.Ui.TeachersSide

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.unacademy.Activities.LectureActivity
import com.example.unacademy.Adapter.RecyclerAdapterLectureTeachersSide
import com.example.unacademy.Adapter.RecyclerAdapterTeachersSideHomePage
import com.example.unacademy.Adapter.StudentSideAdapters.RecyclerAdapterQuizTEachersSide
import com.example.unacademy.R
import com.example.unacademy.Repository.Response
import com.example.unacademy.databinding.FragmentHomePageTeachersSideBinding
import com.example.unacademy.models.StudentSideModel.getStudentSeries.getStudentSeriesItem
import com.example.unacademy.viewmodel.HomePageViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch

class HomePageTeachersSide : Fragment() {

    companion object
    {
        var seriesid:Int?=null
        var latesSeries:List<getStudentSeriesItem>?=null
        var quizid:Int=0
    }
    lateinit var binding :FragmentHomePageTeachersSideBinding
    lateinit var homePageViewModel:HomePageViewModel
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter:RecyclerAdapterTeachersSideHomePage
    lateinit var adapterGetQuiz: RecyclerAdapterQuizTEachersSide
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
        binding.shimmerFrameLayoutQuiz.startShimmerAnimation()
        lifecycleScope.launch {
            homePageViewModel.getSeries()
            homePageViewModel.getQuiz().observe(viewLifecycleOwner
            ) {
                when (it) {
                    is Response.Success -> {
                        binding.shimmerFrameLayoutQuiz.stopShimmerAnimation()
                        binding.shimmerFrameLayoutQuiz.visibility=View.INVISIBLE
                        layoutManager =LinearLayoutManager(container?.context ,LinearLayoutManager.HORIZONTAL, false)
                        binding.recyclerViewQuiz.layoutManager = layoutManager
                        adapterGetQuiz = RecyclerAdapterQuizTEachersSide(it.data)
                        binding.recyclerViewQuiz.adapter = adapterGetQuiz
                        adapterGetQuiz.onClickListener(object : RecyclerAdapterQuizTEachersSide.ClickListener {
                            override fun OnClick(position: Int) {
                                quizid= adapterGetQuiz.studentSideGetQuizModelItem!![position].id
                                findNavController().navigate(R.id.quizShowTEachers)
                            }
                        })
                    }
                }
            }
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
                   binding.seeMoreSeries.visibility=View.VISIBLE
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