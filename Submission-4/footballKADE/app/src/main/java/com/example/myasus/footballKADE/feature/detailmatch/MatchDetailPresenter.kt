package com.example.myasus.footballKADE.feature.detailmatch

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.example.myasus.footballKADE.db.MatchFavorite
import com.example.myasus.footballKADE.db.database
import com.example.myasus.footballKADE.entity.MatchEvents
import com.example.myasus.footballKADE.entity.MatchResponse
import com.example.myasus.footballKADE.entity.TeamDetailResponse
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.entity.api.SportsDbApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class MatchDetailPresenter(
    private val view : DetailView,
    private val apiRepo: ApiRepo,
    private val gson: Gson
){
    fun getMatchDetails(id: String){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepo
                .doRequest(SportsDbApi.getMatchDetail(id)).await(),
                MatchResponse::class.java)

                view.hideLoading()
                view.showMatchDetail(data.match)

        }
    }
    fun getTeamBadge(idHome: String, idAway:String){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val dataHome = gson.fromJson(apiRepo
                .doRequest(SportsDbApi.getTeamDetail(idHome)).await(),
                TeamDetailResponse::class.java)
            val dataAway = gson.fromJson(apiRepo
                .doRequest(SportsDbApi.getTeamDetail(idAway)).await(),
                TeamDetailResponse::class.java)

                view.hideLoading()
                view.showBandgeTeam(dataHome.teams, dataAway.teams)

        }
    }

    fun favoriteState(context: Context, id: String): Boolean{
        var aFavorite = false

        context.database.use{
            val result = select(MatchFavorite.TABLE_FAVORITES)
                .whereArgs(MatchFavorite.ID_EVENT+ " = {id}",
                    "id" to id)
            val favorite = result.parseList(classParser<MatchFavorite>())

            aFavorite =!favorite.isEmpty()
        }
        return aFavorite
    }

    fun removeFromFavorite(context: Context, id: String){
        try{
            context.database.use {
                delete(MatchFavorite.TABLE_FAVORITES,
                    MatchFavorite.ID_EVENT+ " = {id}",
                    "id" to id)
            }
        }catch (e: SQLiteConstraintException){
            context.toast("Error: ${e.message}")

        }
    }

    fun addToFavorite(context: Context, match : MatchEvents){
        try{
            context.database.use{
                insert(MatchFavorite.TABLE_FAVORITES,
                    MatchFavorite.ID_EVENT to match.idEvent,
                    MatchFavorite.DATE to match.dateEvent,
                    //home
                    MatchFavorite.HOME_ID to match.idHomeTeam,
                    MatchFavorite.HOME_TEAM to match.strHomeTeam,
                    MatchFavorite.HOME_SCORE to match.intHomeScore,
                    /*MatchFavorite.HOME_GOAL_DETAILS to match.strHomeGoalDetails,
                    MatchFavorite.HOME_LINEUP_GOALKEEPER to match.strHomeLineupGoalkeeper,
                    MatchFavorite.HOME_LINEUP_DEFENSE to match.strHomeLineupDefense,
                    MatchFavorite.HOME_LINEUP_MIDFIELD to match.strHomeLineupMidfield,
                    MatchFavorite.HOME_LINEUP_FORWARD to match.strHomeLineupForward,
                    MatchFavorite.HOME_LINEUP_SUBSTITUTES to match.strHomeLineupSubstitutes,
                    */
                    //away
                    MatchFavorite.AWAY_ID to match.idAwayTeam,
                    MatchFavorite.AWAY_TEAM to match.strAwayTeam,
                    MatchFavorite.AWAY_SCORE to match.intAwayScore
                    /*MatchFavorite.AWAY_GOAL_DETAILS to match.strAwayGoalDetails,
                    MatchFavorite.AWAY_LINEUP_GOALKEEPER to match.strAwayLineupGoalkeeper,
                    MatchFavorite.AWAY_LINEUP_DEFENSE to match.strAwayLineupDefense,
                    MatchFavorite.AWAY_LINEUP_MIDFIELD to match.strAwayLineupMidfield,
                    MatchFavorite.AWAY_LINEUP_FORWARD to match.strAwayLineupForward,
                    MatchFavorite.AWAY_LINEUP_SUBSTITUTES to match.strAwayLineupSubstitutes*/
                )
            }
        }catch (e : SQLiteConstraintException){
            context.toast("Error: ${e.message}")
        }
    }
}