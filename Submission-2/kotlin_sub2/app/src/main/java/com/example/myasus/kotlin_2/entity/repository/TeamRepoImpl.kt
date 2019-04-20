package com.example.myasus.kotlin_2.entity.repository

import com.example.myasus.kotlin_2.entity.Teams
import com.example.myasus.kotlin_2.rest.FtRest
import io.reactivex.Flowable

class TeamRepoImpl(val ftRest: FtRest) : TeamRepo{

    override fun getAllTeam(id: String)= ftRest.getAllTeam(id)
    override fun getTeam(id: String): Flowable<Teams> = ftRest.getAllTeam(id)
    override fun getTeamDetail(id: String): Flowable<Teams> = ftRest.getTeam(id)

}