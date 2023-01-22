package com.grad.dawinii.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grad.dawinii.databinding.ActivitySplashBinding
import com.grad.dawinii.util.Constants
import com.grad.dawinii.view.authentication.AuthActivity
import com.grad.dawinii.view.main.MainScreenActivity
import com.grad.dawinii.viewModel.AuthViewModel
import io.paperdb.Paper

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private lateinit var authViewModel: AuthViewModel

    var email:String? = null
    var password:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Paper.init(this)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        authViewModel.userMutableLiveData.observe(this, Observer {
            if (it != null) {
                startActivity(Intent(this, MainScreenActivity::class.java))
                finish()
            } else {
                Handler().postDelayed({startActivity(Intent(this, AuthActivity::class.java))
                    finish()}, 2000)
            }
        })

        readUserCredentials()
        login()

    }

    private fun readUserCredentials() {
        email = Paper.book().read<String>(Constants.EMAIL_PAPER_KEY)
        password = Paper.book().read<String>(Constants.PASSWORD_PAPER_KEY)
    }

    private fun login() {
        if (email != null && password != null) {
            authViewModel.logIn(email as String, password as String)
        } else {
            Handler().postDelayed({startActivity(Intent(this, AuthActivity::class.java))
                finish()}, 2000)
        }
    }
}