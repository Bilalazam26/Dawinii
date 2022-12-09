package com.grad.dawinii.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.grad.dawinii.R
import com.grad.dawinii.databinding.FragmentSignUpBinding

class signUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        initView()
        return binding.root
    }

    @SuppressLint("ResourceType")
    private fun initView() {
        val months = resources.getStringArray(R.array.months)
        val adapterMonth = ArrayAdapter<String>(context as Context,android.R.layout.simple_spinner_dropdown_item,months)
        val spinnerAdapter= object : ArrayAdapter<String>(context as Context,android.R.layout.simple_spinner_item, months){
            override fun isEnabled(position: Int): Boolean {
                return position !=0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view :TextView = super.getDropDownView(position, convertView, parent) as TextView
                if (position==0)
                {
                    view.setTextColor(Color.GRAY)
                }
                else{
                    view.setTextColor(Color.BLACK)
                }
                return view
            }
        }
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMonth.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if(value == months[0]){
                    (view as TextView).setTextColor(Color.GRAY)
                }
            }

        }
        binding.spinnerMonth.adapter = spinnerAdapter

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            signUpFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}