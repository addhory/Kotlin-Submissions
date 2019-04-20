package com.example.myasus.footballKADE.feature.mainactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.R.id.*
import com.example.myasus.footballKADE.R.layout.awal_activity
import com.example.myasus.footballKADE.feature.FavoriteFragment
import com.example.myasus.footballKADE.feature.MatchFragment
import com.example.myasus.footballKADE.feature.team.TeamFragment
import kotlinx.android.synthetic.main.awal_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(awal_activity)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                matchesLast -> {
                    loadMatchList(savedInstanceState)
                }
                teams -> {
                    loadTeamList(savedInstanceState)

                }
                favorites -> {
                    loadFavorite(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = matchesLast
    }

    private fun loadTeamList(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, TeamFragment(), TeamFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadMatchList(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MatchFragment(), MatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavorite(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, FavoriteFragment(), FavoriteFragment::class.java.simpleName)
                .commit()
        }
    }
}
