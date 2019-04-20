package com.example.myasus.footballKADE.feature.upcomingmatch

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
import android.widget.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.R.color.colorAccent
import com.example.myasus.footballKADE.adapter.MatchAdapter
import com.example.myasus.footballKADE.entity.Id
import com.example.myasus.footballKADE.entity.MatchEvents
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.feature.detailmatch.DetailActivity
import com.example.myasus.footballKADE.utils.invisible
import com.example.myasus.footballKADE.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class UpcomingFragment : Fragment(), AnkoComponent<Context>, MatchView{


    private var events : MutableList<MatchEvents> = mutableListOf()
    private lateinit var presenter : UpcomingPresenter
    private lateinit var adapter: MatchAdapter
    private lateinit var spinner: Spinner
    private lateinit var listMatch : RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = MatchAdapter(events){
            val intent = Intent(context?.applicationContext, DetailActivity::class.java)
            intent.putExtra("ids", Id("${it.idEvent}", "${it.idHomeTeam}",
                "${it.idAwayTeam}")
            )
            startActivity(intent)
        }
        listMatch.adapter=adapter

        val request = ApiRepo()
        val gson = Gson()
        presenter= UpcomingPresenter(this, request, gson)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                when(leagueName){
                    "English Premier League" -> presenter.getMatchList("4328")
                    "German Bundesliga" -> presenter.getMatchList("4331")
                    "Italian Serie A" -> presenter.getMatchList("4332")
                    "French Ligue 1" -> presenter.getMatchList("4334")
                    "Spanish La Liga" -> presenter.getMatchList("4335")
                    "Netherlands Eredivisie" -> presenter.getMatchList("4337")


                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        swipeRefresh.onRefresh {
            when(leagueName){
                "English Premier League" -> presenter.getMatchList("4328")
                "German Bundesliga" -> presenter.getMatchList("4331")
                "Italian Serie A" -> presenter.getMatchList("4332")
                "French Ligue 1" -> presenter.getMatchList("4334")
                "Spanish La Liga" -> presenter.getMatchList("4335")
                "Netherlands Eredivisie" -> presenter.getMatchList("4337")


            }
            progressBar.invisible()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.spinner
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listMatch = recyclerView {
                        id = R.id.list_match
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventList(matchList: List<MatchEvents>) {
        swipeRefresh.isRefreshing=false
        events.clear()
        events.addAll(matchList)
        adapter.notifyDataSetChanged()
    }

}