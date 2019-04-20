package com.example.myasus.footballKADE.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamItem (
    val id: Long?,
    @SerializedName("idTeam")
    val idTeam: String?,

    @SerializedName("strTeamBadge")
    val strTeamBadge: String?,
    @SerializedName("strTeam")
    val strTeam: String? ,

    @SerializedName("intFormedYear")
    val intFormedYear: String? ,

    @SerializedName("strStadium")
    val strStadium: String? ,
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String?

) : Parcelable
{
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