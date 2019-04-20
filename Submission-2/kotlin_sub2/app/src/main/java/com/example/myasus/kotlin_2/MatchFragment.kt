package com.example.myasus.kotlin_2

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myasus.kotlin_2.adapter.ViewPagerAdapter
import com.example.myasus.kotlin_2.lastmatch.LastMatchFrag
import com.example.myasus.kotlin_2.upcomingmatch.UpcomingFrag
import com.example.myasus.kotlin_2.upcomingmatch.UpcomingPresenter

class MatchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vPager = view.findViewById<ViewPager>(R.id.viewpager)
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        val adapter = ViewPagerAdapter (childFragmentManager)

        setHasOptionsMenu(true)
        adapter.populateFragment(LastMatchFrag(), "Last Match")
        adapter.populateFragment(UpcomingFrag(), "Upcoming Match")

        vPager.adapter = adapter
        tabs.setupWithViewPager(vPager)
    }
}