package com.example.myasus.kotlin_2.upcomingmatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myasus.kotlin_2.MatchContract
import com.example.myasus.kotlin_2.R
import com.example.myasus.kotlin_2.adapter.MatchAdapter
import com.example.myasus.kotlin_2.entity.matchEvent
import com.example.myasus.kotlin_2.entity.repository.MatchRepoImpl
import com.example.myasus.kotlin_2.lastmatch.LastMatchPresenter
import com.example.myasus.kotlin_2.rest.FtApiService
import com.example.myasus.kotlin_2.rest.FtRest
import com.example.myasus.kotlin_2.utils.Scheduler.AppSchedulerProv
import com.example.myasus.kotlin_2.utils.invisible
import com.example.myasus.kotlin_2.utils.visible
import kotlinx.android.synthetic.main.frag_last_match.*
import kotlinx.android.synthetic.main.frag_upcoming_match.*

class UpcomingFrag : Fragment(), MatchContract.View{

    lateinit var presenter : UpcomingPresenter
    lateinit var leagueName : String

    private var matches : MutableList<matchEvent> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val service = FtApiService.getClient().create(FtRest::class.java)
        val request = MatchRepoImpl(service)
        val scheduler = AppSchedulerProv()

        presenter = UpcomingPresenter(this,request,scheduler)
        presenter.getFootballMatchData()

        val spinnerItems = resources.getStringArray(R.array.leagueArray)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerMatchesUp.adapter = spinnerAdapter

        spinnerMatchesUp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinnerMatchesUp.selectedItem.toString()

                when(leagueName){
                    "English Premier League" -> presenter.getFootballMatchData("4328")
                    "German Bundesliga" -> presenter.getFootballMatchData("4331")
                    "Italian Serie A" -> presenter.getFootballMatchData("4332")
                    "French Ligue 1" -> presenter.getFootballMatchData("4334")
                    "Spanish La Liga" -> presenter.getFootballMatchData("4335")
                    "Netherlands Eredivisie" -> presenter.getFootballMatchData("4337")

                    else -> presenter.getFootballMatchData()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_upcoming_match, container, false)

    }

    override fun hideLoading() {
        mainProgressBarUp.invisible()
        rvFootballUpcoming.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mainProgressBarUp.visible()
        rvFootballUpcoming.visibility = View.INVISIBLE
    }

    override fun displayFootballMatch(matchList: List<matchEvent>) {
        matches.clear()
        matches.addAll(matchList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvFootballUpcoming.layoutManager = layoutManager
        rvFootballUpcoming.adapter = MatchAdapter(matchList, context)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyPresenter()
    }

}