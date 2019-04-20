package com.example.myasus.kotlin_2.rest

import com.example.myasus.kotlin_2.entity.Teams
import com.example.myasus.kotlin_2.entity.ftMatch
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface FtRest {

    @GET("eventspastleague.php")
    fun getLastmatch(@Query("id") id:String) : Flowable<ftMatch>

    @GET("eventsnextleague.php")
    fun getUpcomingMatch(@Query("id") id:String) : Flowable<ftMatch>

    @GET("lookupevent.php")
    fun getEventById(@Query("id") id:String) : Flowable<ftMatch>

    @GET("lookup_all_teams.php")
    fun getAllTeam(@Query("id") id:String) : Flowable<Teams>

    @GET("lookupteam.php")
    fun getTeam(@Query("id") id:String) : Flowable<Teams>

}