package com.example.myasus.footballKADE.feature.team

import com.example.myasus.footballKADE.entity.TeamItem

interface TeamView{
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: MutableList<TeamItem>)
    fun emptyData()

}