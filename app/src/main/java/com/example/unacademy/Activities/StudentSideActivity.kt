package com.example.unacademy.Activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.unacademy.R
import com.example.unacademy.Ui.Auth.Splash_Screen
import com.example.unacademy.databinding.ActivityStudentSideBinding
import kotlinx.coroutines.launch

lateinit var binding:ActivityStudentSideBinding
class StudentSideActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentSideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_student_side) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        binding.bottomNavigationViewStudentSide.setupWithNavController(navController)
        var menu_button = findViewById<ImageView>(R.id.MenuButton)
        var notification_button=findViewById<ImageView>(R.id.NotificationButtonStudentSide)
        notification_button.setOnClickListener(this)
        menu_button.setOnClickListener {
            val dialodView =
                LayoutInflater.from(this).inflate(R.layout.fragment_dialog__box, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(dialodView)

            val alertDialog: AlertDialog = mBuilder.create()
            alertDialog.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE)
            alertDialog.show()
            dialodView.findViewById<ImageView>(R.id.LogOutButton).setOnClickListener(this)
            dialodView.findViewById<ImageView>(R.id.Feedback).setOnClickListener(this)
            dialodView.findViewById<ImageView>(R.id.Feedback).setOnClickListener(this)
            dialodView.findViewById<ImageView>(R.id.changePasswordDialogBox).setOnClickListener {
                var navController = Navigation.findNavController(this,R.id.fragment_container_student_side)
                navController.navigate(R.id.change_Password_Inside)
                alertDialog.cancel()
            }

            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window?.setLayout(
                400,
                2000
            )
            alertDialog.window?.setGravity(Gravity.LEFT)

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.LogOutButton -> {

                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("Confirm Exit")
                builder.setMessage("Are you sure you want to LogOut ?")
                builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    finishAffinity()
                    lifecycleScope.launch {
                        Splash_Screen.save("studentloggedIn", false)
                    }
                })
                builder.setNegativeButton(
                    "No",
                    DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
                builder.show()
            }
            R.id.Feedback -> {
                var intent: Intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("mailto:" + "unacademy.software.incubator@gmail.com")
                );
                intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                startActivity(intent);
            }
            R.id.NotificationButtonStudentSide->
            {
                var navController = Navigation.findNavController(this,R.id.fragment_container_student_side)
                navController.navigate(R.id.notificationsStudentSide)
            }
        }
    }

    override fun onBackPressed() {
        when (findNavController(R.id.fragment_container_student_side).currentDestination?.id) {
            R.id.questionPageStudentSide -> alertBox()
            R.id.homePageStudentSide->alertBoxClose()
            else -> super.onBackPressed()
        }
    }

    private fun alertBox()
    {
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Leave Quiz")
            .setMessage("Are you sure you want to leave the quiz?")
            .setPositiveButton("No"){dialog,id->dialog.cancel()}
            .setNegativeButton("Yes"){dialog,id->super.onBackPressed()}
        builder.show()
    }
    private fun alertBoxClose()
    {
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Leave Quiz")
            .setMessage("Are you sure you want to leave the App?")
            .setPositiveButton("No"){dialog,id->dialog.cancel()}
            .setNegativeButton("Yes"){dialog,id->finishAffinity()}
        builder.show()
    }
}