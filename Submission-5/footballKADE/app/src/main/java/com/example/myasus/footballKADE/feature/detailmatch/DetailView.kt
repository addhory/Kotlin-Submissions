package com.example.myasus.footballKADE.feature.detailmatch

import com.example.myasus.footballKADE.entity.MatchEvents
import com.example.myasus.footballKADE.entity.TeamItem

interface DetailView{
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<MatchEvents>)
    fun showBandgeTeam(dataHome : List<TeamItem>, dataAway: List<TeamItem>)
    fun emptyData()
}