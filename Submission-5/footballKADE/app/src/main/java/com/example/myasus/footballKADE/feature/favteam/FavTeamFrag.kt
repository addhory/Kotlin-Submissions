package com.example.myasus.footballKADE.feature.favteam

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.adapter.TeamAdapter
import com.example.myasus.footballKADE.db.TeamFavorite
import com.example.myasus.footballKADE.db.database
import com.example.myasus.footballKADE.entity.TeamItem
import com.example.myasus.footballKADE.feature.detailteam.DetailTeamActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavTeamFrag : Fragment(), AnkoComponent<Context>{

    private var favTeam : MutableList<TeamItem> = mutableListOf()
    private lateinit var adapter : TeamAdapter
    private lateinit var listMatch : RecyclerView
    private lateinit var swipeRefresh : SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter= TeamAdapter(favTeam){
            DetailTeamActivity.start(context, it)
        }
        showFavTeam()
        listMatch.adapter=adapter
        swipeRefresh.onRefresh {
            showFavTeam()
        }
    }

    private fun showFavTeam(){
        favTeam.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing=false
            val result = select(TeamItem.TABLE_TEAMS).orderBy("TEAM_NAME", SqlOrderDirection.ASC)
            val favorite=result.parseList(classParser<TeamItem>())
            favTeam.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavTeam()
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
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                listMatch=recyclerView {
                    id = R.id.list_favTeam
                    lparams(width= matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }
}