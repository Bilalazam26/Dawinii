package com.grad.dawinii.view.drawer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.grad.dawinii.R
import com.grad.dawinii.databinding.ActivityProfileBinding
import com.grad.dawinii.model.entities.User
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.decodeStringToImageUri
import com.grad.dawinii.viewModel.LocalViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        updateView(Prevalent.currentUser)
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

    private fun updateView(user: User?) {
        binding.apply {
            userName.text = user?.name
            userPhone.text = user?.phone
            userGender.text = user?.gender
            userMail.text = user?.email
            userAge.text = user?.age.toString()
            Glide.with(this@ProfileActivity).load(decodeStringToImageUri(this@ProfileActivity, user?.image.toString())).placeholder(R.drawable.profile_circle).into(userProfilePicture)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}