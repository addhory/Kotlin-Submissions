package com.example.myasus.kotlin_sub3.feature.upcomingmatch

import com.example.myasus.kotlin_sub3.entity.MatchEvents

interface MatchView{
    fun showLoading()
    fun hideLoading()
    fun showEventList(matchList: List<MatchEvents>)
}