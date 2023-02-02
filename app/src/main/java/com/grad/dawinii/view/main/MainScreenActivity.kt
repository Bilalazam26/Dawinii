//Bilal is here
package com.grad.dawinii.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.grad.dawinii.R
import com.grad.dawinii.databinding.ActivityMainScreenBinding
import com.grad.dawinii.view.AddRoutineActivity

class MainScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_fragment)
        binding.mainScreenNav.setupWithNavController(navController)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}