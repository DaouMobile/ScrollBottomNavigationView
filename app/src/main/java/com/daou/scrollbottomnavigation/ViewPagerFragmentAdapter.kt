package com.daou.scrollbottomnavigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragmentAdapter(
    fragmentActivity: FragmentActivity,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]
}