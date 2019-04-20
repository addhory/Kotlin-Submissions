package com.example.myasus.kotlin_2.entity.repository

import com.example.myasus.kotlin_2.entity.Teams
import io.reactivex.Flowable

interface TeamRepo{

    fun getTeam(id : String = "0") : Flowable<Teams>
    fun getTeamDetail(id : String ="0") : Flowable<Teams>
    fun getAllTeam(id : String) : Flowable<Teams>
}