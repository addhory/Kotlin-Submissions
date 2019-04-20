package com.example.myasus.kotlin_2.entity

import com.google.gson.annotations.SerializedName

data class ftMatch (
    @SerializedName("events") var events: List<matchEvent>
)