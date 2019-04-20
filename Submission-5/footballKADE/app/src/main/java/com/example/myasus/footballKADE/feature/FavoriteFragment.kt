package com.example.myasus.footballKADE.feature

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.adapter.ViewPagerAdapter
import com.example.myasus.footballKADE.feature.fatMatch.FavMatchFrag
import com.example.myasus.footballKADE.feature.favteam.FavTeamFrag
import kotlinx.android.synthetic.main.fragment_match.*

class FavoriteFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        with(activity as AppCompatActivity){
            setSupportActionBar(toolbar)
        }
        val viPager = view.findViewById<ViewPager>(R.id.viewpagerFav)
        val tabsFav = view.findViewById<TabLayout>(R.id.tabsFav)
        val adapterFav = ViewPagerAdapter(childFragmentManager)
        adapterFav.populateFragment(FavMatchFrag(),"Match")
        adapterFav.populateFragment(FavTeamFrag(), "Team")

        viPager.adapter=adapterFav
        tabsFav.setupWithViewPager(viPager)
    }
}