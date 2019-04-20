package com.example.myasus.footballKADE.feature.upcomingmatch

import com.example.myasus.footballKADE.entity.MatchResponse
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.entity.api.SportsDbApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpcomingPresenter(
    private val view: MatchView,
    private val apiRepo: ApiRepo,
    private val gson: Gson
){
     fun getMatchList(league: String?){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepo
                .doRequest(SportsDbApi.getLeagueUpcoming(league)).await()
            ,MatchResponse::class.java)

                view.hideLoading()
                view.showEventList(data.match)

        }
    }

}