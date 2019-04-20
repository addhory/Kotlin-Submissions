package com.example.myasus.kotlin_2.detail

import com.example.myasus.kotlin_2.entity.Team

interface DetailView {
    interface View{
        fun displayTeamBadgeHome(team: Team)
        fun displayTeamBadgeAway(team: Team)

    }

    interface Presenter{
        fun getTeamBadgeAway(id: String)
        fun getTeamBadgeHome(id: String)
        fun onDestroyPresenter()
    }
}