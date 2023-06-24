//Bilal is here
package com.grad.dawinii.view.main


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.grad.dawinii.R
import com.grad.dawinii.databinding.ActivityMainScreenBinding
import com.grad.dawinii.databinding.DrawerHeaderBinding
import com.grad.dawinii.util.Constants
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.decodeStringToImageUri
import com.grad.dawinii.util.makeToast
import com.grad.dawinii.view.authentication.AuthActivity
import com.grad.dawinii.view.drawer.AboutUsActivity
import com.grad.dawinii.view.drawer.ProfileActivity
import com.grad.dawinii.viewModel.AuthViewModel
import io.paperdb.Paper


class MainScreenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var binding: ActivityMainScreenBinding
    private lateinit var navController: NavController
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
        initView()
    }

    private fun initView() {
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setupDrawerAndToolBar()
        setupBottomNav()
    }

    private fun setupBottomNav() {
        navController = findNavController(R.id.nav_fragment)
        val popUpMenu = PopupMenu(this, null)
        popUpMenu.inflate(R.menu.main_screen_bnv)
        val menu = popUpMenu.menu
        binding.mainScreenNav.setupWithNavController(menu, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

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

        val headerView = navigationView.getHeaderView(0)
        val headerImg = headerView.findViewById<ImageView>(R.id.user_pic_in_drawer)
        val headerName = headerView.findViewById<TextView>(R.id.tv_user_name_in_drawer)

        headerName.text = Prevalent.currentUser?.name
        Glide.with(this).load(decodeStringToImageUri(this, Prevalent.currentUser?.image.toString())).placeholder(R.drawable.profile_circle).into(headerImg)
        Log.d("MAIN", "${Prevalent.currentUser}")
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



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> startActivity(Intent(this, ProfileActivity::class.java))
            R.id.logout -> logOut()
            R.id.about -> startActivity(Intent(this,AboutUsActivity::class.java))
        }
        return true
    }

    private fun logOut() {
        authViewModel.logOut()
    }


}