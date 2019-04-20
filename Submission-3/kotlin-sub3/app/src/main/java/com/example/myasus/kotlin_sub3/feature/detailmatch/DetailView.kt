package com.example.myasus.kotlin_sub3.feature.detailmatch

import com.example.myasus.kotlin_sub3.entity.MatchEvents
import com.example.myasus.kotlin_sub3.entity.TeamBadge
import com.example.myasus.kotlin_sub3.entity.TeamDetailResponse

interface DetailView{
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<MatchEvents>)
    fun showBandgeTeam(dataHome : List<TeamBadge>, dataAway: List<TeamBadge>)
}