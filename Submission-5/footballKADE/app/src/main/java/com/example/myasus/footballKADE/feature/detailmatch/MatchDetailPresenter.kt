package com.example.myasus.footballKADE.feature.detailmatch

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.provider.CalendarContract
import com.example.myasus.footballKADE.db.MatchFavorite
import com.example.myasus.footballKADE.db.database
import com.example.myasus.footballKADE.entity.MatchEvents
import com.example.myasus.footballKADE.entity.MatchResponse
import com.example.myasus.footballKADE.entity.TeamResponse
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.entity.api.SportsDbApi
import com.example.myasus.footballKADE.utils.DateFormat
import com.example.myasus.footballKADE.utils.dateTimeToFormat
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MatchDetailPresenter(
    private val view : DetailView?,
    private val apiRepo: ApiRepo,
    private val gson: Gson
){
    fun getMatchDetails(id: String){
        view?.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val data = gson.fromJson(apiRepo
                    .doRequest(SportsDbApi.getMatchDetail(id)).await(),
                    MatchResponse::class.java)

                view?.hideLoading()
                view?.showMatchDetail(data.match)
            }catch (e: Exception){
                view?.emptyData()
            }

        }
    }
    fun getTeamBadge(idHome: String, idAway:String){
        view?.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val dataHome = gson.fromJson(apiRepo
                    .doRequest(SportsDbApi.getTeamDetail(idHome)).await(),
                    TeamResponse::class.java)
                val dataAway = gson.fromJson(apiRepo
                    .doRequest(SportsDbApi.getTeamDetail(idAway)).await(),
                    TeamResponse::class.java)

                view?.hideLoading()
                view?.showBandgeTeam(dataHome.teams, dataAway.teams)
            }catch (e: Exception){
                view?.emptyData()
            }

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
                    MatchFavorite.DATE_TIME to match.strTime,
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
    /*fun addNotif(context: Context, match: MatchEvents){
        val intent = Intent(Intent.ACTION_INSERT)
        intent.type = "vnd.android.cursor.item/event"
        val tglWaktu= match.dateEvent + " " + match.strTime
        val start= tglWaktu.dateTimeToFormat(tglWaktu)
        val akhir = start + TimeUnit.MINUTES.toMillis(90)

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, akhir)
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
        intent.putExtra(CalendarContract.CalendarAlerts.ALARM_TIME, TimeUnit.MINUTES.toMillis(90))
        intent.putExtra(CalendarContract.Events.TITLE, "${match.strHomeTeam} vs ${match.strAwayTeam}" )
        intent.putExtra(CalendarContract.Events.DESCRIPTION,
            "Big Match ${match.strHomeTeam} vs ${match.strAwayTeam} Must Watch!!")

        context.startActivity(intent)
    }*/
}