package com.grad.dawinii.view.drawer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.grad.dawinii.R
import com.grad.dawinii.databinding.ActivityEditProfileBinding
import com.grad.dawinii.model.entities.User
import com.grad.dawinii.util.Constants
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.decodeStringToImageUri
import com.grad.dawinii.util.encodeImageUriToString
import com.grad.dawinii.util.makeToast
import com.grad.dawinii.viewModel.AuthViewModel
import com.grad.dawinii.viewModel.LocalViewModel

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var localViewModel: LocalViewModel
    private lateinit var launcher: ActivityResultLauncher<String>
    private var imageStr = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]

        supportActionBar?.apply {
            title = null
            setDisplayHomeAsUpEnabled(true)
        }

        launcher = registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
            if (it != null){
                binding.imageProfile.setImageURI(it)
                imageStr = encodeImageUriToString(this, it).toString()
            }
        })

        initView()
    }

    private fun initView() {
        updateView(Prevalent.currentUser)
        binding.imageProfile.setOnClickListener {
            openGallery()
        }
        binding.btnProfileUpdate.setOnClickListener {
            updateUser()
        }
    }

    private fun updateUser() {
        val name = binding.etProfileName.text.toString()
        val address = binding.etProfileAddress.text.toString()
        val phone = binding.etProfilePhone.text.toString()
        val trustedPerson = binding.etProfileTrustedPerson.text.toString()
        val age = binding.etProfileAge.text.toString().toInt()
        val gender = getGender()
        if (!(address.isNullOrEmpty() || name.isNullOrEmpty() || phone.isNullOrEmpty() || trustedPerson.isNullOrEmpty())) {
            if (age < 12) {
                makeToast(this, "Only +12")
            } else {
                var user = Prevalent.currentUser as User
                user.name = name
                user.address = address
                user.phone = phone
                user.trustPerson = trustedPerson
                user.age = age
                user.gender = gender
                user.image = imageStr

                localViewModel.updateLocalUser(user)
                val authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
                authViewModel.updateUser(user)

            }
        } else {
            makeToast(this, "Empty Field!")
        }
    }

    private fun updateView(user: User?) {
        binding.etProfileAddress.setText(user?.address)
        binding.etProfileName.setText(user?.name)
        binding.etProfilePhone.setText(user?.phone)
        binding.etProfileTrustedPerson.setText(user?.trustPerson)
        binding.etProfileAge.setText(user?.age.toString())
        setGender(user?.gender)

        Glide.with(this).load(decodeStringToImageUri(this, user?.image.toString())).placeholder(R.drawable.profile_circle).into(binding.imageProfile)
    }

    private fun setGender(gender: String?) {
        when (gender) {
            Constants.GENDER_MALE -> binding.rbgGender.check(R.id.rbMale)
            Constants.GENDER_FEMALE -> binding.rbgGender.check(R.id.rbFemale)
        }
    }
    private fun getGender(): String {
        var gender:String = ""
        when(binding.rbgGender.checkedRadioButtonId) {
            binding.rbMale.id -> gender = Constants.GENDER_MALE
            binding.rbFemale.id -> gender = Constants.GENDER_FEMALE
        }
        return gender
    }

    private fun openGallery() {
        launcher.launch("image/*")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}