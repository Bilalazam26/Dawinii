package com.grad.dawinii.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.grad.dawinii.databinding.ActivityAddRoutineBinding

class AddRoutineActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddRoutineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRoutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        binding.backBtn.setOnClickListener { finish() }

    }
}