package com.grad.dawinii.view.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.grad.dawinii.databinding.FragmentResetPasswordBinding
import com.grad.dawinii.viewModel.AuthViewModel

class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding
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
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        initView()
    }

    private fun initView() {
        binding.resetPasswordEmailSendBtn.setOnClickListener {
            val email = binding.etResetPasswordEmail.text.toString()
            sendResetPasswordEmail(email)
        }
    }

    private fun sendResetPasswordEmail(email: String) {
        authViewModel.resetPassword(email)
        closeFragment()
    }

    private fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ResetPasswordFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}