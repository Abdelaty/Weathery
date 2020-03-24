package com.example.forecastmvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.forecastmvvm.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)
//        val input = "15cfb621f5407c5143a916c86bb0b674596246a62ebd094990a30b40e97ab5a73fbf86af8"
//        val md5 = input.md5()
//        println("computed md5 value is $md5")
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(null, navController)
    }

//    fun String.md5(): String {
//        val md = MessageDigest.getInstance("MD5")
//        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
//    }
}

