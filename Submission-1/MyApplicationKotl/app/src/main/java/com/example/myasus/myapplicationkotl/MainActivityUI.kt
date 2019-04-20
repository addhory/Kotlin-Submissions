package com.example.myasus.myapplicationkotl

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myasus.myapplicationkotl.MainActivity
import com.example.myasus.myapplicationkotl.RecyclerViewAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivityUI (private val mAdapter: RecyclerViewAdapter) : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {

        return relativeLayout {
            padding = dip(10)
            lparams(width = matchParent, height = wrapContent)

            recyclerView {
                lparams(width = matchParent, height = wrapContent)
                layoutManager = LinearLayoutManager(ctx)
                adapter = mAdapter
            }
        }
    }
}