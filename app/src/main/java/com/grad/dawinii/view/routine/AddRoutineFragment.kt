package com.grad.dawinii.view.routine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding.saveBtn.setOnClickListener {
            addRoutine()
        }
    }

    private fun addRoutine() {
        val routineName = binding.etAddRoutineName.text.toString()
        binding.addMedicineBtn.setOnClickListener {
            addMedicine()
        }
    }

    private fun addMedicine() {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddRoutineFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}