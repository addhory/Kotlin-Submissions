package com.example.myasus.footballKADE.entity

import com.google.gson.annotations.SerializedName


data class MatchResponse(
   @SerializedName("events") var match: List<MatchEvents>)