package com.example.myasus.kotlin_sub3.feature.detailmatch

import com.example.myasus.kotlin_sub3.entity.MatchResponse
import com.example.myasus.kotlin_sub3.entity.TeamDetailResponse
import com.example.myasus.kotlin_sub3.entity.api.ApiRepo
import com.example.myasus.kotlin_sub3.entity.api.SportsDbApi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailPresenter(
    private val view : DetailView,
    private val apiRepo: ApiRepo,
    private val gson: Gson
){
    fun getMatchDetails(id: String){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepo
                .doRequest(SportsDbApi.getMatchDetail(id)),
                MatchResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showMatchDetail(data.match)
            }
        }
    }
    fun getTeamBadge(idHome: String, idAway:String){
        view.showLoading()
        doAsync {
            val dataHome = gson.fromJson(apiRepo
                .doRequest(SportsDbApi.getTeamDetail(idHome)),
                TeamDetailResponse::class.java)
            val dataAway = gson.fromJson(apiRepo
                .doRequest(SportsDbApi.getTeamDetail(idAway)),
                TeamDetailResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showBandgeTeam(dataHome.teams, dataAway.teams)
            }
        }
    }
}