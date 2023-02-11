//Bilal is here
package com.grad.dawinii.view.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.grad.dawinii.R
import com.grad.dawinii.databinding.ActivityMainScreenBinding
import com.grad.dawinii.util.makeToast

class MainScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainScreenBinding
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
        initView()
    }

    private fun initView() {
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setupDrawerAndToolBar()
        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_fragment)
        binding.mainScreenNav.setupWithNavController(navController)
    }

    private fun setupDrawerAndToolBar() {
        setSupportActionBar(binding.toolbar)
        toggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.navForDrawer.itemIconTintList = null
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navForDrawer.setNavigationItemSelectedListener {
            makeToast(applicationContext,"item selected")
            true
        }
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
}