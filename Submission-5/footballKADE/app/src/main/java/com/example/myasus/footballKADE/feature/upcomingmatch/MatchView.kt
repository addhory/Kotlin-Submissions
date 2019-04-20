package com.example.myasus.footballKADE.feature.upcomingmatch

import com.example.myasus.footballKADE.entity.MatchEvents

interface MatchView{
    fun showLoading()
    fun hideLoading()
    fun showEventList(matchList: List<MatchEvents>)
    fun emptyData()
}