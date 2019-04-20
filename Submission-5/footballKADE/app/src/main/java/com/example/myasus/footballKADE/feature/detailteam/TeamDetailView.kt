package com.example.myasus.footballKADE.feature.detailteam

import com.example.myasus.footballKADE.entity.TeamItem


interface TeamDetailView{
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<TeamItem>)
    fun emptyData()

}