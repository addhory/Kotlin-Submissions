package com.example.myasus.footballKADE.entity

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teams") val teams: MutableList<TeamItem>
)