package com.example.myasus.footballKADE.feature.searchmatch

import android.util.Log
import com.example.myasus.footballKADE.entity.MatchSearchRes
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.entity.api.SportsDbApi
import com.example.myasus.footballKADE.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchMatchPresenter(
    private val view: SearchMatchView?,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
)
{
    fun getMatch(e : String = ""){
        view?.showLoading()

        GlobalScope.launch(context.main){
            try{
                val data = gson.fromJson(apiRepo.doRequest(SportsDbApi.getMatchSearch(e))
                    .await(), MatchSearchRes::class.java)
                Log.d("data", "data adalah " + data)

                view?.hideLoading()
                view?.showMatchList(data.events)
            }catch (e: Exception){
                view?.emptyData()
            }
        }

    }
}