package com.grad.dawinii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grad.dawinii.R
import com.grad.dawinii.databinding.AnalysisLayoutBinding
import com.grad.dawinii.model.entities.Analysis

class AnalysisAdapter: RecyclerView.Adapter<AnalysisAdapter.AnalysisVH>() {
    val analyses = mutableListOf<Analysis>()
    lateinit var context:Context
    inner class AnalysisVH(view:View): RecyclerView.ViewHolder(view){
        val binding = AnalysisLayoutBinding.bind(view)
        val item = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalysisVH {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.analysis_layout,parent,false)
        return AnalysisVH(view)
    }

    override fun getItemCount(): Int = analyses.size

    override fun onBindViewHolder(holder: AnalysisVH, position: Int) {
        val item = analyses[position]
        holder.binding.analysisDate.text = item.analysisDate
    }

    fun addData(analyses: Collection<Analysis>) {
        this.analyses.clear()
        this.analyses.addAll(analyses)
        notifyDataSetChanged()
    }
}