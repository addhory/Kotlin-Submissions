package com.example.myasus.footballKADE.feature.upcomingmatch

import com.example.myasus.footballKADE.entity.MatchResponse
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.entity.api.SportsDbApi
import com.example.myasus.footballKADE.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class UpcomingPresenter(
    private val view: MatchView?,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){
     fun getMatchList(league: String?){
        view?.showLoading()
        GlobalScope.launch(context.main) {
            try{
                val data = gson.fromJson(apiRepo
                    .doRequest(SportsDbApi.getLeagueUpcoming(league)).await()
                    ,MatchResponse::class.java)
                view?.hideLoading()

                view?.showEventList(data.match)
            }catch (e: Exception){
                view?.emptyData()
            }

        }
    }

}