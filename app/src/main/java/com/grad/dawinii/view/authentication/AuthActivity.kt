package com.grad.dawinii.view.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.grad.dawinii.databinding.ActivityAuthBinding
import com.grad.dawinii.adapter.PagerAdapter
import com.grad.dawinii.util.Constants
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.view.main.MainScreenActivity
import com.grad.dawinii.viewModel.LocalViewModel
import io.paperdb.Paper

class AuthActivity : AppCompatActivity() {
    private lateinit var localViewModel: LocalViewModel
    lateinit var binding :ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "onCreate: 1")
        binding = ActivityAuthBinding.inflate(layoutInflater)
        Paper.init(this)
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
        Log.d("TAG", "onCreate: 2")
        localViewModel.userMutableLiveData.observe(this) {
            if (it != null) {
                Prevalent.currentUser = it
                startActivity(Intent(this, MainScreenActivity::class.java))
                Log.d("TAG", "onCreate: 3")
                finish()
            }
            Log.d("TAG", "onCreate: 4")
        }
        Log.d("TAG", "onCreate: 5")
        readUser()
        Log.d("TAG", "onCreate: 6")
        setContentView(binding.root)
        Log.d("TAG", "onCreate: 7")
        setupPager()
        Log.d("TAG", "onCreate: 8")
    }
    private fun readUser() {
        val uid = Paper.book().read<String>(Constants.UID_PAPER_KEY)
        if (!uid.isNullOrEmpty()) {
            localViewModel.getLocalUser(uid)
        }
    }
    private fun setupPager() {
        val adapter = PagerAdapter(this)
        adapter.addFragment(LogInFragment(),"LOGIN")
        adapter.addFragment(SignUpFragment(),"SIGNUP")
        binding.pager.adapter = adapter
        TabLayoutMediator(binding.tabLayout,binding.pager){tab , position ->
            tab.text = adapter.titles[position]
        }.attach()
    }



    fun resetPassword() {
        binding.resetPasswordFragment.visibility = View.VISIBLE
    }
}