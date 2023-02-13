package com.grad.dawinii.view.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.grad.dawinii.R
import com.grad.dawinii.databinding.ActivityProfileBinding
import com.grad.dawinii.model.entities.User
import com.grad.dawinii.util.Constants
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.makeToast
import com.grad.dawinii.viewModel.AuthViewModel
import com.grad.dawinii.viewModel.LocalViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var localViewModel: LocalViewModel
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCustomActionBar()

        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]

        initView()
    }

    private fun setCustomActionBar() {
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

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
        if (!name.isNullOrEmpty()) {
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
                localViewModel.updateLocalUser(user)
                val authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
                authViewModel.updateUser(user)
                makeToast(this, "Updated")
            }
        } else {
            makeToast(this, "Name cannot be empty!")
        }

    }

    private fun updateView(user: User) {
        toolbar.title = user.name
        binding.etProfileAddress.setText(user.address)
        binding.etProfileName.setText(user.name)
        binding.etProfilePhone.setText(user.phone)
        binding.etProfileTrustedPerson.setText(user.trustPerson)
        binding.etProfileAge.setText(user.age.toString())
        setGender(user.gender)
    }

    private fun setGender(gender: String) {
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
}