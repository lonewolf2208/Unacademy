package com.example.unacademy.Ui.Auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unacademy.Activities.NavBarActivity
import com.example.unacademy.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class Splash_Screen : Fragment() {

    companion object{

        var dataStore: DataStore<Preferences>? = null
        suspend fun save(key:String,value:Boolean)
        {
            val dataStoreKey= preferencesKey<Boolean>(key)
            dataStore?.edit { Settings->
                Settings[dataStoreKey]=value
            }

        }
        suspend fun readInfo(key:String):String?
        {
            val dataStoreKey= preferencesKey<String>(key)
            val preferences = dataStore?.data?.first()
            return preferences?.get(dataStoreKey)
        }
        suspend fun saveInfo(key:String,value:String)
        {
            val dataStoreKey= preferencesKey<String>(key)
            dataStore?.edit { Settings->
                Settings[dataStoreKey]=value
            }

        }
        suspend fun read(key:String):Boolean?
        {
            val dataStoreKey= preferencesKey<Boolean>(key)
            val preferences = dataStore?.data?.first()
            return preferences?.get(dataStoreKey)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStore = context?.createDataStore(name = "Settings")!!
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_splash__screen, container, false)
        android.os.Handler().postDelayed(
            {
                lifecycleScope.launch {
                    var teacherloggedIn = read("teacherloggedIn")
                    if (teacherloggedIn!= true)
                    {
                        val intent = Intent(activity, NavBarActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        findNavController().navigate(R.id.action_splash_Screen_to_signUpLoginPage)
                    }
                }
            },3000)
        return view
    }


}