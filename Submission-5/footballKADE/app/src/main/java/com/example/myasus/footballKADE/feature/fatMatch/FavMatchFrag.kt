package com.example.myasus.footballKADE.feature.fatMatch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.R.color.colorAccent
import com.example.myasus.footballKADE.db.MatchFavorite
import com.example.myasus.footballKADE.db.database
import com.example.myasus.footballKADE.entity.IdEvent
import com.example.myasus.footballKADE.feature.detailmatch.DetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavMatchFrag : Fragment(), AnkoComponent<Context>{

    private var favMatch : MutableList<MatchFavorite> = mutableListOf()
    private lateinit var adapter : FavMatchAdapter
    private lateinit var listMatch : RecyclerView
    private lateinit var swipeRefresh : SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter= FavMatchAdapter(favMatch){
            val intent = Intent(context?.applicationContext, DetailActivity::class.java)
            intent.putExtra("ids", IdEvent("${it.idEvent}", "${it.idHomeTeam}",
                "${it.idAwayTeam}")
            )
            startActivity(intent)
        }
        showFavorite()
        listMatch.adapter=adapter
        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }


    private fun showFavorite(){
        favMatch.clear()
        context?.database?.use{
            swipeRefresh.isRefreshing=false
            val result = select(MatchFavorite.TABLE_FAVORITES).orderBy("DATE", SqlOrderDirection.DESC)
            val favorite = result.parseList(classParser<MatchFavorite>())
            favMatch.addAll(favorite)
            adapter.notifyDataSetChanged()

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with (ui) {
        linearLayout {
            lparams(width = matchParent, height= wrapContent)
            topPadding=dip(16)
            leftPadding= dip(16)
            rightPadding=dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                listMatch=recyclerView {
                    id = R.id.list_match
                    lparams(width= matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }

}