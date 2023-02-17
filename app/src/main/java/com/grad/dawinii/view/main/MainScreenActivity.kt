//Bilal is here
package com.grad.dawinii.view.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.grad.dawinii.R
import com.grad.dawinii.databinding.ActivityMainScreenBinding
import com.grad.dawinii.databinding.DrawerHeaderBinding
import com.grad.dawinii.util.Constants
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.makeToast
import com.grad.dawinii.view.authentication.AuthActivity
import com.grad.dawinii.view.profile.ProfileActivity
import com.grad.dawinii.viewModel.AuthViewModel
import com.grad.dawinii.viewModel.LocalViewModel
import io.paperdb.Paper

class MainScreenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var binding: ActivityMainScreenBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Paper.init(this)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        authViewModel.isLoggedOutMutableLiveData.observe(this) {
            if (it) {
                Prevalent.currentUser = null
                Paper.book().write<String>(Constants.UID_PAPER_KEY, "")
                startActivity(Intent(this, AuthActivity::class.java))
                makeToast(this, "Logged Out")
            }
        }
        createNotificationChannel()
        initView()
    }

    private fun initView() {
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setupDrawerAndToolBar()
        setupBottomNav()
        setDrawerView()
    }

    private fun setDrawerView() {
        val binding = DrawerHeaderBinding.inflate(layoutInflater)
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_fragment)
        binding.mainScreenNav.setupWithNavController(navController)
    }

    private fun setupDrawerAndToolBar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        toggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        val navigationView = binding.navForDrawer
        navigationView.itemIconTintList = null
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener(this)
        toolbar.title = Prevalent.currentUser?.name

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "DawiniiReminderChannel"
            val description = "Medicine Reminder"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("Dawinii", name, importance)
            channel.description = description
            val notificationManager =
                ContextCompat.getSystemService(this, NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        makeToast(this@MainScreenActivity, "${item.title}")
        when (item.itemId) {
            R.id.profile -> startActivity(Intent(this, ProfileActivity::class.java))
            R.id.logout -> logOut()
        }
        return true
    }

    private fun logOut() {
        authViewModel.logOut()
    }


}