package com.example.myasus.footballKADE.feature.mainactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.R.id.*
import com.example.myasus.footballKADE.R.layout.awal_activity
import com.example.myasus.footballKADE.feature.fatMatch.FavMatchFrag
import com.example.myasus.footballKADE.feature.lastmatch.LastMatchFrag
import com.example.myasus.footballKADE.feature.upcomingmatch.UpcomingFragment
import kotlinx.android.synthetic.main.awal_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(awal_activity)


        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                matchesLast ->{
                    loadMatchLast(savedInstanceState)
                }
                matchesUp ->{
                    loadMatchUp(savedInstanceState)

                }
                favorites ->{
                    loadFavorite(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = matchesLast
    }

    private fun loadMatchUp(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, UpcomingFragment(), UpcomingFragment::class.java.simpleName)
                .commit()
        }
    }
    private fun loadMatchLast(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, LastMatchFrag(), LastMatchFrag::class.java.simpleName)
                .commit()
        }
    }
    private fun loadFavorite(savedInstanceState: Bundle?){
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, FavMatchFrag(), FavMatchFrag::class.java.simpleName)
                .commit()
        }
    }
}
