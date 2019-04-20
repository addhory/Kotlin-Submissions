package com.example.myasus.footballKADE.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamFavorite(
    val id : Long?,
    val idTeam : String?,
    val strTeamBadge:String?,
    val strTeam: String?,
    val intFormedYear: String?,
    val strStadium: String?,
    val strDescriptionEN: String
) : Parcelable {

    companion object {
        const val TABLE_TEAMS="TABLE_TEAMS"
        const val ID="ID"
        const val ID_TEAM ="ID_TEAM"
        const val TEAM_BADGE="TEAM_BADGE"
        const val TEAM_NAME = "TEAM_NAME"
        const val TEAM_YEAR = "TEAM_YEAR"
        const val TEAM_STADIUM = "TEAM_STADIUM"
        const val DESCRIPTION = "DESCRIPTION"
    }
}
