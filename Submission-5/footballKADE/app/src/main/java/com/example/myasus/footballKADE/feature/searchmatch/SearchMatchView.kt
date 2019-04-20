package com.example.myasus.footballKADE.feature.searchmatch

import com.example.myasus.footballKADE.entity.MatchEvents

interface SearchMatchView{
    fun showLoading()
    fun hideLoading()
    fun emptyData()
    fun showMatchList(data : MutableList<MatchEvents>)
}