package com.grad.dawinii.view.drawer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.grad.dawinii.databinding.ActivityProfileBinding
import com.grad.dawinii.model.entities.User
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.viewModel.LocalViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var localViewModel: LocalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
        initView()
    }

    override fun onStart() {
        super.onStart()
        localViewModel.getLocalUser(Prevalent.currentUser?.id as String)
        localViewModel.userMutableLiveData.observe(this) {
            if (it != null) {
                updateView(it)
                Prevalent.currentUser = it
            }
        }
    }

    private fun initView() {
        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
        customizeToolBar()
    }

    private fun customizeToolBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
            title = binding.userName.text
        }
    }

    private fun updateView(user: User) {
        binding.apply {

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}