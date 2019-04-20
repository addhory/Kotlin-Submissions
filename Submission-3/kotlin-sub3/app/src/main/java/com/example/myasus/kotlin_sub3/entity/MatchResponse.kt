package com.example.myasus.kotlin_sub3.entity

import com.google.gson.annotations.SerializedName


data class MatchResponse(
    @SerializedName("events") var match: List<MatchEvents>)