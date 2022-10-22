package com.alex.android.git

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onSupportNavigateUp() = navController.navigateUp()

}