package com.example.myasus.kotlin_2

import com.example.myasus.kotlin_2.entity.matchEvent

interface MatchContract {
    interface View{
        fun hideLoading()
        fun showLoading()
        fun displayFootballMatch(matchList:List<matchEvent>)
    }

    interface Presenter{
        fun getFootballMatchData(leagueName: String = "4328")
        fun onDestroyPresenter()

    }
}