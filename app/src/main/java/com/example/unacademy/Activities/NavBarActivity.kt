package com.example.unacademy.Activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.unacademy.R
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.Ui.TeachersSide.HomePageTeachersSide
import com.example.unacademy.Ui.TeachersSide.ProfileTeachersSide
import com.example.unacademy.databinding.ActivityNavBarBinding
import com.example.unacademy.databinding.FragmentLogInBinding
import kotlinx.coroutines.launch

class NavBarActivity : AppCompatActivity() ,View.OnClickListener{

    private lateinit var binding: ActivityNavBarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNavBarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.itemIconTintList=null
        binding.bottomNavigationView.background=null
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        var menu_button=findViewById<ImageView>(R.id.MenuButton)
    menu_button.setOnClickListener {
        val dialodView =
            LayoutInflater.from(this).inflate(R.layout.fragment_dialog__box, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(dialodView)

        val alertDialog:AlertDialog=mBuilder.create()
        alertDialog.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE)
        alertDialog.show()
        dialodView.findViewById<ImageView>(R.id.LogOutButton).setOnClickListener(this)
        dialodView.findViewById<ImageView>(R.id.Feedback).setOnClickListener(this)
        dialodView.findViewById<ImageView>(R.id.Feedback).setOnClickListener(this)
        dialodView.findViewById<ImageView>(R.id.changePasswordDialogBox).setOnClickListener {
            var navController = Navigation.findNavController(this,R.id.fragment_container)
            navController.navigate(R.id.emailVerification)
            alertDialog.cancel()
        }

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.window?.setLayout(400,
            2000)
        alertDialog.window?.setGravity(Gravity.LEFT)

    }


        binding.floatingButtonTeachersSide.setOnClickListener {
            navController.navigate(R.id.createYourSeries)
        }
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }





    override fun onClick(v: View?) {
       when(v?.id)
       {
           R.id.LogOutButton->
           {
               lifecycleScope.launch {
                   Splash_Screen.save("loggedIn",false)
               }
               val builder: AlertDialog.Builder = AlertDialog.Builder(this)
               builder.setTitle("Confirm Exit")
               builder.setMessage("Are you sure you want to LogOut ?")
               builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which -> finishAffinity()})
               builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->dialog.cancel()  })
               builder.show()

           }
           R.id.Feedback->
           {
               var intent:Intent =  Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "unacademy.software.incubator@gmail.com"));
               intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
               intent.putExtra(Intent.EXTRA_TEXT, "your_text");
               startActivity(intent);
           }

       }
    }
}