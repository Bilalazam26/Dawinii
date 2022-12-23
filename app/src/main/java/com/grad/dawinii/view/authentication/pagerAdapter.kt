package com.grad.dawinii.view.authentication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    val fragments = ArrayList<Fragment>()
    val titles = ArrayList<String>()
    fun addFragment(fragment: Fragment,title:String){
        this.fragments.add(fragment)
        this.titles.add(title)
    }
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}