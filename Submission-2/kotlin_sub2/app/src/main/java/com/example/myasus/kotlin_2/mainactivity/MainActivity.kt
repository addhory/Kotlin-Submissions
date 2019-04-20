package com.example.myasus.kotlin_2.mainactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.myasus.kotlin_2.MatchFragment
import com.example.myasus.kotlin_2.R
import kotlinx.android.synthetic.main.awal_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.awal_activity)

        setSupportActionBar(toolbar_main)

        loadMatch(savedInstanceState)
    }

    private fun loadMatch(savedInstanceState: Bundle?){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MatchFragment(), MatchFragment::class.java.simpleName)
            .commit()
    }
}
