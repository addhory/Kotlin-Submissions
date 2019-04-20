package com.example.myasus.kotlin_2.entity.repository

import com.example.myasus.kotlin_2.entity.ftMatch
import com.example.myasus.kotlin_2.rest.FtRest
import io.reactivex.Flowable

class MatchRepoImpl(private val ftRest: FtRest) : MatchRepo {


    override fun getEventById(id: String): Flowable<ftMatch> = ftRest.getEventById(id)

    override fun getUpcomingMatch(id: String): Flowable<ftMatch> = ftRest.getUpcomingMatch(id)

    override fun getLastMatch(id: String): Flowable<ftMatch> = ftRest.getLastmatch(id)
}