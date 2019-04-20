package com.example.myasus.footballKADE.feature

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.adapter.ViewPagerAdapter
import com.example.myasus.footballKADE.feature.lastmatch.LastMatchFrag
import com.example.myasus.footballKADE.feature.searchmatch.SearchMatchActivity
import com.example.myasus.footballKADE.feature.upcomingmatch.UpcomingFragment
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.startActivity

class MatchFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        with(activity as AppCompatActivity){
            setSupportActionBar(toolbar)
        }

        val vPager = view.findViewById<ViewPager>(R.id.viewpager)
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        val adapter = ViewPagerAdapter(childFragmentManager)
        setHasOptionsMenu(true)
        adapter.populateFragment(LastMatchFrag(), "Previous")
        adapter.populateFragment(UpcomingFragment(), "Upcoming")
        vPager.adapter = adapter
        tabs.setupWithViewPager(vPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.menu_search -> {
                context?.startActivity<SearchMatchActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}