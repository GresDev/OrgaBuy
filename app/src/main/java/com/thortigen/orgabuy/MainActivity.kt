package com.thortigen.orgabuy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navigationController = findNavController(R.id.nav_host_fragment_container)
        bottomNavigationView.setupWithNavController(navigationController)

        setSupportActionBar(findViewById(R.id.app_toolbar))

        setupActionBarWithNavController(navigationController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navigationController = findNavController(R.id.nav_host_fragment_container)
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }
}