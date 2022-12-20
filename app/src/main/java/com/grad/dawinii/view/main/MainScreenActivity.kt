//Bilal is here
package com.grad.dawinii.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.grad.dawinii.R
import com.grad.dawinii.authentication.AuthActivity
import com.grad.dawinii.databinding.ActivityMainScreenBinding
import com.grad.dawinii.util.Constants
import com.grad.dawinii.viewModel.AuthViewModel
import io.paperdb.Paper

class MainScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainScreenBinding
    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val navController = findNavController(R.id.nav_fragment)
        binding.mainScreenNav.setupWithNavController(navController)

        Paper.init(this)

        initView()
    }

    private fun initView() {
        binding.logoutBtn.setOnClickListener {
            authViewModel.logOut()
            Paper.book().destroy()
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }


}