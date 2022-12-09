package com.grad.dawinii.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.grad.dawinii.databinding.ActivityAuthBinding
import com.grad.dawinii.util.PagerAdapter

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
        adapter.addFragment(logInFragment(),"LOGIN")
        adapter.addFragment(signUpFragment(),"SIGNUP")
        binding.pager.adapter = adapter
        TabLayoutMediator(binding.tabLayout,binding.pager){tab , position ->
            tab.text = adapter.titles[position]
        }.attach()
    }
}