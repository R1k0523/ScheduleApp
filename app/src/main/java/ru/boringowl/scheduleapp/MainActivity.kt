package ru.boringowl.scheduleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        NavigationUI.setupWithNavController(findViewById<BottomNavigationView>(R.id.nav_view), navHost.navController)
    }
}