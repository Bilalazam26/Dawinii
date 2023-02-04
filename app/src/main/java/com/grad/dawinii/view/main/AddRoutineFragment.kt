package com.grad.dawinii.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grad.dawinii.R
import com.grad.dawinii.databinding.FragmentAddRoutineBinding

class AddRoutineFragment : Fragment() {
    lateinit var binding: FragmentAddRoutineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoutineBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        setDoctorView()
    }

    private fun setDoctorView() {
        binding.noDoctorRb.setOnClickListener {
            if (binding.noDoctorRb.isChecked) {
                binding.doctorInfoLayout.visibility = View.GONE
            }
        }

        binding.doctorRb.setOnClickListener {
            if (binding.doctorRb.isChecked) {
                binding.doctorInfoLayout.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddRoutineFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}