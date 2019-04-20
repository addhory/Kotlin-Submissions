package com.example.myasus.footballKADE.feature.favteam

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.example.myasus.footballKADE.db.TeamFavorite
import com.example.myasus.footballKADE.db.database
import com.example.myasus.footballKADE.entity.TeamItem
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class FavUtils{
    fun addToFav(context: Context, data : TeamItem){
        try{
            context.database.use {
                insert(TeamItem.TABLE_TEAMS,
                    TeamItem.ID_TEAM to data.idTeam,
                    TeamItem.TEAM_BADGE to data.strTeamBadge,
                    TeamItem.TEAM_NAME to data.strTeam,
                    TeamItem.TEAM_STADIUM to data.strStadium,
                    TeamItem.TEAM_YEAR to data.intFormedYear,
                    TeamItem.DESCRIPTION to data.strDescriptionEN)
            }
        } catch (e:SQLiteConstraintException){
            context.toast("Error: ${e.message}")
        }
    }

    fun removeFromFav(context: Context, data: TeamItem){
        try {
            context.database.use {
                delete(TeamItem.TABLE_TEAMS,
                    TeamItem.ID_TEAM + " = {id}",
                    "id" to data.idTeam.toString())
            }
        }catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun isFav(context:Context, data: TeamItem) : Boolean{
        var tFavorite = false

        context.database.use{
            val result = select(TeamItem.TABLE_TEAMS)
                .whereArgs(TeamItem.ID_TEAM+ " = {id}",
                    "id" to data.idTeam.toString())
            val favorite = result.parseList(classParser<TeamItem>())

            tFavorite=! favorite.isEmpty()
        }
        return tFavorite
    }
}