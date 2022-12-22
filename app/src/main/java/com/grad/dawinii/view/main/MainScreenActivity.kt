//Bilal is here
package com.grad.dawinii.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.grad.dawinii.R
import com.grad.dawinii.databinding.ActivityMainScreenBinding

class MainScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_fragment)
        binding.mainScreenNav.setupWithNavController(navController)

    }
}