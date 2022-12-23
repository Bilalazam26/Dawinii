package com.grad.dawinii.view.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.grad.dawinii.R
import com.grad.dawinii.databinding.ActivityAuthBinding
import com.grad.dawinii.view.main.MainScreenActivity
import com.grad.dawinii.util.Constants
import com.grad.dawinii.viewModel.AuthViewModel
import io.paperdb.Paper

class AuthActivity : AppCompatActivity() {

    lateinit var binding :ActivityAuthBinding

    var email:String? = null
    var password:String? = null

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        authViewModel.userMutableLiveData.observe(this, Observer {
            if (it != null) {
                startActivity(Intent(this, MainScreenActivity::class.java))
                finish()
            }
        })

        Paper.init(this)

        readUserCredentials()
        login()


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

    private fun readUserCredentials() {
        email = Paper.book().read<String>(Constants.EMAIL_PAPER_KEY)
        password = Paper.book().read<String>(Constants.PASSWORD_PAPER_KEY)
    }

    private fun login() {
        if (email != null && password != null) {
            authViewModel.logIn(email as String, password as String)
        }
    }

    fun googleLogIn() {
        supportFragmentManager.beginTransaction().replace(R.id.pager, GoogleLogInFragment()).commit()
    }

    fun resetPassword() {
        binding.resetPasswordFragment.visibility = View.VISIBLE
    }
}