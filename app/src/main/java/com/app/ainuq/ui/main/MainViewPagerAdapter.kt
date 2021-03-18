package com.app.ainuq.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.ainuq.ui.home.HomeFragment
import com.app.ainuq.ui.orders.OrdersFragment
import com.app.ainuq.ui.search.SearchFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> OrdersFragment.newInstance()
            2 -> SearchFragment.newInstance()
            else -> throw Exception("Index not found")
        }
    }
}