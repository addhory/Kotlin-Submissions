package com.example.myasus.footballKADE.feature.lastmatch

import com.example.myasus.footballKADE.entity.MatchResponse
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.entity.api.SportsDbApi
import com.example.myasus.footballKADE.feature.upcomingmatch.MatchView
import com.example.myasus.footballKADE.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class LastMatchPresenter(
    private val view: MatchView?,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getMatchLastList(league: String?){
        view?.showLoading()
        GlobalScope.launch(context.main) {
            try{
                val data = gson.fromJson(apiRepo
                    .doRequest(SportsDbApi.getLeagueLast(league)).await()
                    , MatchResponse::class.java)
                view?.hideLoading()

                view?.showEventList(data.match)

            }catch (e: Exception){
                view?.emptyData()
            }
        }
    }

}