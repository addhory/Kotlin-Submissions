package com.example.myasus.footballKADE.feature.detailteam.player

import com.example.myasus.footballKADE.entity.PlayerItem

interface PlayerView{
    fun showLoading()
    fun hideLoading()
    fun emptyData()
    fun showPlayerList(data: List<PlayerItem>)
}