package com.example.myasus.footballKADE.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IdEvent (
    val id:String,
    val idHome: String,
    val idAway: String
) : Parcelable