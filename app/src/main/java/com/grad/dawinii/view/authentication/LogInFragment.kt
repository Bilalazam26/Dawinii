package com.grad.dawinii.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grad.dawinii.databinding.FragmentLogInBinding
import com.grad.dawinii.view.main.MainScreenActivity
import com.grad.dawinii.viewModel.AuthViewModel


class logInFragment : Fragment() {
    private lateinit var binding: FragmentLogInBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        authViewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                startActivity(Intent(context, MainScreenActivity::class.java))
                activity?.onBackPressed()
                activity?.finish()
            }
        })
        initView()
    }

    private fun initView() {
        binding.logInBtn.setOnClickListener {
            logIn()
        }

        binding.googleBtn.setOnClickListener {
            googleLogIn()
        }

        binding.forgotPasswordBtn.setOnClickListener {
            resetPassword()
        }
    }

    private fun googleLogIn() {
        val authActivity = activity as AuthActivity
        authActivity.googleLogIn()
    }

    private fun resetPassword() {
        val authActivity = activity as AuthActivity
        authActivity.resetPassword()
    }

    private fun logIn() {
        val email = binding.etLogInEmail.text.toString()
        val password = binding.etLogInPassword.text.toString()

        if (!(email.isNullOrEmpty() || password.isNullOrEmpty())) {
            authViewModel.logIn(email, password)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            logInFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}