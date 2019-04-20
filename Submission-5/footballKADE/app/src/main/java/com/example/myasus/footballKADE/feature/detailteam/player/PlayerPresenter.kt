package com.example.myasus.footballKADE.feature.detailteam.player

import com.example.myasus.footballKADE.entity.PlayerResponse
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.entity.api.SportsDbApi
import com.example.myasus.footballKADE.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class PlayerPresenter(
    private val view : PlayerView?,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context : CoroutineContextProvider = CoroutineContextProvider()
){
    fun getPlayerList(teamName: String=""){
        view?.showLoading()
        GlobalScope.launch(context.main){
            try{
                val data = gson.fromJson(apiRepo
                    .doRequest(SportsDbApi.getPlayerList(teamName)).await()
                    , PlayerResponse::class.java
                )
                view?.hideLoading()
                view?.showPlayerList(data.player)
            }catch (e:Exception){
                view?.emptyData()
            }
        }
    }
}