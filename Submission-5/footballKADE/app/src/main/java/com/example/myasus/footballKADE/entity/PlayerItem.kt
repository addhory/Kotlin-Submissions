package com.example.myasus.footballKADE.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerItem(
    @SerializedName("idPlayer") var idPlayer : String? = null,
    @SerializedName("idTeam") var idTeam : String? = null,
    @SerializedName("strPosition") var strPosition : String? = null,
    @SerializedName("strPlayer") var strPlayer : String? = null,
    @SerializedName("strWeight") var strWeight : String? = null,
    @SerializedName("strHeight") var strHeight : String? = null,
    @SerializedName("strDescriptionEN") var strDescriptionEN : String? = null,
    @SerializedName("strCutout") var strCutout: String? = null,
    @SerializedName("strFanart1") var strFanart1 : String? = null
    ) : Parcelable