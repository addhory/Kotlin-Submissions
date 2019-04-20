package com.example.myasus.footballKADE.entity

import com.google.gson.annotations.SerializedName

data class MatchSearchRes(

    @field:SerializedName("event")
    val events: MutableList<MatchEvents>
)