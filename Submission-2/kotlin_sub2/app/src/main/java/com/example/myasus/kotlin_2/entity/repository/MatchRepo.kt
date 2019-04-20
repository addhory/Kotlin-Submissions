package com.example.myasus.kotlin_2.entity.repository

import com.example.myasus.kotlin_2.entity.ftMatch
import io.reactivex.Flowable

interface MatchRepo {

    fun getLastMatch(id : String) : Flowable<ftMatch>

    fun getUpcomingMatch(id : String) : Flowable<ftMatch>

    fun getEventById(id: String) : Flowable<ftMatch>

}