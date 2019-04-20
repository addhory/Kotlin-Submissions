package com.example.myasus.kotlin_sub3.feature.upcomingmatch

import com.example.myasus.kotlin_sub3.entity.MatchResponse
import com.example.myasus.kotlin_sub3.entity.api.ApiRepo
import com.example.myasus.kotlin_sub3.entity.api.SportsDbApi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UpcomingPresenter(
    private val view: MatchView,
    private val apiRepo: ApiRepo,
    private val gson: Gson
){
     fun getMatchList(league: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepo
                .doRequest(SportsDbApi.getLeagueUpcoming(league))
            ,MatchResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showEventList(data.match)
            }
        }
    }

}