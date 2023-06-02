package com.example.newsapp.ui.top_headlines

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.ui.business_news.BusinessNewsFragment
import com.example.newsapp.ui.us_news.USNewsFragment

private const val NUM_TABS = 2

class ViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = NUM_TABS

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return USNewsFragment()
            1 -> return BusinessNewsFragment()
        }
        return USNewsFragment()
    }
}