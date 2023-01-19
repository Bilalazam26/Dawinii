package com.grad.dawinii.view.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.grad.dawinii.databinding.ActivityAuthBinding
import com.grad.dawinii.adapter.PagerAdapter

class AuthActivity : AppCompatActivity() {

    lateinit var binding :ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)




        setupPager()
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