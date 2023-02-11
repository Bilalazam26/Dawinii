package com.grad.dawinii.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.grad.dawinii.R
import com.grad.dawinii.databinding.FragmentProfileBinding
import com.grad.dawinii.model.entities.User
import com.grad.dawinii.util.Constants
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.makeToast
import com.grad.dawinii.viewModel.AuthViewModel
import com.grad.dawinii.viewModel.LocalViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private lateinit var localViewModel: LocalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
        localViewModel.getLocalUser(Prevalent.currentUser?.id as String)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localViewModel.userMutableLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                updateView(it)
                Prevalent.currentUser = it
            }
        }

        initView()
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
        if (!(address.isNullOrEmpty() || name.isNullOrEmpty() || phone.isNullOrEmpty() || trustedPerson.isNullOrEmpty())) {
            if (age < 12) {
                makeToast(context, "Only +12")
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
            }
        } else {
            makeToast(context, "Empty Field!")
        }

    }

    private fun updateView(user: User) {
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

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}