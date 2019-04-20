package com.example.myasus.footballKADE.feature.detailmatch

import com.example.myasus.footballKADE.entity.MatchEvents
import com.example.myasus.footballKADE.entity.TeamBadge

interface DetailView{
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<MatchEvents>)
    fun showBandgeTeam(dataHome : List<TeamBadge>, dataAway: List<TeamBadge>)
}