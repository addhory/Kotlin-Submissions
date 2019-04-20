package com.example.myasus.footballKADE.feature.team

import com.example.myasus.footballKADE.entity.TeamResponse
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.entity.api.SportsDbApi
import com.example.myasus.footballKADE.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class TeamPresenter(
    private val view: TeamView?,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getTeamList(league: String?){
        view?.showLoading()

        GlobalScope.launch(context.main) {
                val data = gson.fromJson(apiRepo
                    .doRequest(SportsDbApi.getTeamList(league))
                    .await(), TeamResponse::class.java)

                view?.hideLoading()
            try{
                view?.showTeamList(data.teams)
            } catch (e: Exception){
                view?.emptyData()
            }
        }
    }

    fun getSearchTeam(t: String = ""){
        view?.showLoading()
        GlobalScope.launch(context.main){
            val data=gson.fromJson(apiRepo.doRequest(SportsDbApi.getTeamSearch(t))
                .await(), TeamResponse::class.java)

            view?.hideLoading()
            try{
                view?.showTeamList(data.teams)
            }catch (e: Exception){

            }
        }
    }
}