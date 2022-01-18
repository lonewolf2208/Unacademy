package com.example.unacademy.Activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.unacademy.R
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.Ui.TeachersSide.ProfileTeachersSide
import com.example.unacademy.databinding.ActivityNavBarBinding
import com.example.unacademy.databinding.FragmentLogInBinding

class NavBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavBarBinding
//    lateinit var toggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNavBarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.background=null
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
//        toggle= ActionBarDrawerToggle(this,binding.drawerlayoutTeachersSide,R.string.open,R.string.close)
//        binding.drawerlayoutTeachersSide.addDrawerListener(toggle)
//        toggle.syncState()
       binding.navgationViewTeachersSide.setOnClickListener{
            val dialodView =
                LayoutInflater.from(this).inflate(R.layout.fragment_dialog__box, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(dialodView)
            mBuilder.show()
            window.setGravity(Gravity.LEFT)
        }

        binding.floatingButtonTeachersSide.setOnClickListener {
            navController.navigate(R.id.createYourSeries)
        }
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId)
        {
            R.id.MenuButton->
            {
                val dialodView =
                    LayoutInflater.from(this).inflate(R.layout.fragment_dialog__box, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(dialodView)
                mBuilder.show()
                window.setGravity(Gravity.LEFT)
                return true
            }
            else->super.onOptionsItemSelected(item)
        }
    }
}