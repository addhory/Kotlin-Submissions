package com.example.myasus.kotlin_sub3.feature.lastmatch

import com.example.myasus.kotlin_sub3.entity.MatchResponse
import com.example.myasus.kotlin_sub3.entity.api.ApiRepo
import com.example.myasus.kotlin_sub3.entity.api.SportsDbApi
import com.example.myasus.kotlin_sub3.feature.upcomingmatch.MatchView
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LastMatchPresenter(
    private val view: MatchView,
    private val apiRepo: ApiRepo,
    private val gson: Gson
){
    fun getMatchLastList(league: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepo
                .doRequest(SportsDbApi.getLeagueLast(league))
                , MatchResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showEventList(data.match)
            }
        }
    }

}