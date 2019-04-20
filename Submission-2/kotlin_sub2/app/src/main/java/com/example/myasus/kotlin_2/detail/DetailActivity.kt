package com.example.myasus.kotlin_2.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myasus.kotlin_2.R
import com.example.myasus.kotlin_2.entity.Team
import com.example.myasus.kotlin_2.entity.matchEvent
import com.example.myasus.kotlin_2.entity.repository.TeamRepoImpl
import com.example.myasus.kotlin_2.rest.FtApiService
import com.example.myasus.kotlin_2.rest.FtRest
import com.example.myasus.kotlin_2.utils.DateFormat
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailView.View{
    lateinit var matchEvent: matchEvent
    lateinit var presenter: DetailPresenter


    override fun displayTeamBadgeHome(team: Team) {
        Glide.with(applicationContext)
            .load(team.strTeamBadge).into(awayImg)
    }

    override fun displayTeamBadgeAway(team: Team) {
        Glide.with(applicationContext)
            .load(team.strTeamBadge).into(homeImg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val service = FtApiService.getClient().create(FtRest::class.java)
        val request = TeamRepoImpl(service)

        presenter = DetailPresenter(this, request)

        matchEvent = intent.getParcelableExtra("match")
        presenter.getTeamBadgeAway(matchEvent.idAwayTeam)
        presenter.getTeamBadgeHome(matchEvent.idHomeTeam)

        initData(matchEvent)
        supportActionBar?.title = matchEvent.strEvent
    }

    fun initData(matchEvent: matchEvent) {
        if (matchEvent.intHomeScore == null) {
            dateTv.setTextColor(applicationContext.getColor(R.color.colorPrimary))
        }

        dateTv.text = matchEvent.dateEvent?.let { DateFormat.formatDateToMatch(it) }
        homeNameTv.text = matchEvent.strHomeTeam
        homeScoreTv.text = matchEvent.intHomeScore
        awayNameTv.text = matchEvent.strAwayTeam
        awayScoreTv.text = matchEvent.intAwayScore

        homePenyetakTv.text = matchEvent.strHomeGoalDetails
        awayPenyetakTv.text = matchEvent.strAwayGoalDetails

        gkHomeTv.text = matchEvent.strHomeLineupGoalkeeper
        gkAwayTv.text = matchEvent.strAwayLineupGoalkeeper

        defHomeTv.text = matchEvent.strHomeLineupDefense
        defAwayTv.text = matchEvent.strAwayLineupDefense

        midHomeTv.text = matchEvent.strHomeLineupMidfield
        midAwayTv.text = matchEvent.strAwayLineupMidfield

        forHomeTv.text = matchEvent.strHomeLineupForward
        forAwayTv.text = matchEvent.strAwayLineupForward

        subHomeTv.text = matchEvent.strHomeLineupSubstitutes
        subAwayTv.text = matchEvent.strAwayLineupSubstitutes

    }

}