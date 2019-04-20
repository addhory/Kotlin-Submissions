package com.example.myasus.kotlin_sub3.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Id (
    val id:String,
    val idHome: String,
    val idAway: String
) : Parcelable