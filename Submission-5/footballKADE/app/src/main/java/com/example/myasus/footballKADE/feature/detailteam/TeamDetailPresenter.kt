package com.example.myasus.footballKADE.feature.detailteam

import com.example.myasus.footballKADE.entity.TeamResponse
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.entity.api.SportsDbApi
import com.example.myasus.footballKADE.feature.upcomingmatch.MatchView
import com.example.myasus.footballKADE.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class TeamDetailPresenter(
    private val view: TeamDetailView?,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()

    ){
    fun getTeamDetails(id: String){
        view?.showLoading()
        GlobalScope.launch(context.main) {
            try {
                val data = gson.fromJson(apiRepo
                    .doRequest(SportsDbApi.getTeamDetail(id)).await()
                    , TeamResponse::class.java)

                view?.hideLoading()
                view?.showTeamDetail(data.teams)
            }catch (e: Exception){
                view?.emptyData()
            }

        }
    }
}