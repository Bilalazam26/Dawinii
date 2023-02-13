package com.grad.dawinii.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grad.dawinii.R
import com.grad.dawinii.databinding.ActivitySplashBinding
import com.grad.dawinii.util.Constants
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.view.authentication.AuthActivity
import com.grad.dawinii.view.main.MainScreenActivity
import com.grad.dawinii.viewModel.AuthViewModel
import com.grad.dawinii.viewModel.LocalViewModel
import io.paperdb.Paper

class SplashActivity : AppCompatActivity() {

    private lateinit var localViewModel: LocalViewModel
    private lateinit var binding: ActivitySplashBinding
    lateinit var logoAnim :Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logoAnim = AnimationUtils.loadAnimation(this, R.anim.splash_logo_anim)
        binding.splashLogo.animation = logoAnim
        Paper.init(this)

        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
        localViewModel.userMutableLiveData.observe(this) {
            if (it != null) {
                Prevalent.currentUser = it
                startActivity(Intent(this, MainScreenActivity::class.java))
                finish()
            } else {
                Handler().postDelayed({startActivity(Intent(this, AuthActivity::class.java))
                    finish()}, 3000)
            }
        }

        readUser()

    }

    private fun readUser() {
        val uid = Paper.book().read<String>(Constants.UID_PAPER_KEY)
        if (!uid.isNullOrEmpty()) {
            localViewModel.getLocalUser(uid)
        } else {
            Handler().postDelayed({startActivity(Intent(this, AuthActivity::class.java))
                finish()}, 3000)
        }
    }
}