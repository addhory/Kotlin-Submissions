package com.example.myasus.footballKADE.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IdPlayer(
    val idPlayer: String,
val idTeam:String
) : Parcelable