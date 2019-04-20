package com.example.myasus.kotlin_sub3.feature.detailmatch

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.myasus.kotlin_sub3.R
import com.example.myasus.kotlin_sub3.R.color.colorAccent
import com.example.myasus.kotlin_sub3.R.drawable.ic_add_to_favorites
import com.example.myasus.kotlin_sub3.R.drawable.ic_added_to_favorites
import com.example.myasus.kotlin_sub3.db.MatchFavorite
import com.example.myasus.kotlin_sub3.db.database
import com.example.myasus.kotlin_sub3.entity.Id
import com.example.myasus.kotlin_sub3.entity.MatchEvents
import com.example.myasus.kotlin_sub3.entity.TeamBadge
import com.example.myasus.kotlin_sub3.entity.api.ApiRepo
import com.example.myasus.kotlin_sub3.utils.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DetailActivity : AppCompatActivity(), DetailView {



    private lateinit var presenter: MatchDetailPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: ScrollView
    private lateinit var swipeRefresh : SwipeRefreshLayout
    private lateinit var match: MatchEvents

    //date
    private lateinit var dateEvent: TextView
    //home
    private lateinit var homeBadge: ImageView
    private lateinit var homeName : TextView
    private lateinit var homeScore : TextView
    private lateinit var homeGoalDetails: TextView
    private lateinit var homeGoalKeeper : TextView
    private lateinit var homeDefense : TextView
    private lateinit var homeMidfield:TextView
    private lateinit var homeForward:TextView
    private lateinit var homeSubtitutes : TextView

    //away
    private lateinit var awayBadge: ImageView
    private lateinit var awayName : TextView
    private lateinit var awayScore : TextView
    private lateinit var awayGoalDetails: TextView
    private lateinit var awayGoalKeeper : TextView
    private lateinit var awayDefense : TextView
    private lateinit var awayMidfield:TextView
    private lateinit var awayForward:TextView
    private lateinit var awaySubtitutes : TextView

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var idEvent: String
    private lateinit var idHome: String
    private lateinit var idAway: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var allId=intent.extras.getParcelable<Id>("ids")

        idEvent=allId.id
        idHome=allId.idHome
        idAway=allId.idAway

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        relativeLayout {
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )
                scrollView = scrollView {
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        padding = dip(16)

                        dateEvent = textView {
                            gravity = Gravity.CENTER
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                            textSize = 20f
                            setTypeface(null, Typeface.BOLD)
                        }
                        //score
                        linearLayout {
                            gravity = Gravity.CENTER

                            homeScore=textView {
                                padding = dip(16)
                                textSize = 48f
                                setTypeface(null, Typeface.BOLD)

                            }
                            textView {
                                padding = dip(16)
                                textSize = 48f
                                setTypeface(null, Typeface.BOLD)
                                text = "vs"
                            }

                            awayScore=textView {
                                padding = dip(16)
                                textSize = 48f
                                setTypeface(null, Typeface.BOLD)

                            }
                        }
                        //badge
                        linearLayout {
                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                homeBadge=imageView {
                                }.lparams {
                                    width = dip(100)
                                    height = dip(100)
                                    gravity = Gravity.CENTER
                                }

                                homeName=textView {
                                    gravity = Gravity.CENTER
                                    textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                    textSize = 24f
                                    setTypeface(null, Typeface.BOLD)

                                }
                            }.lparams(matchParent, wrapContent, 1f)

                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                awayBadge=imageView {
                                }.lparams {
                                    width = dip(100)
                                    height = dip(100)
                                    gravity = Gravity.CENTER
                                }
                                awayName=textView {
                                    gravity = Gravity.CENTER
                                    textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                    textSize = 24f
                                    setTypeface(null, Typeface.BOLD)

                                }

                            }.lparams(matchParent, wrapContent, 1f)
                        }

                        linearLayout {
                            view {
                                backgroundColor = Color.LTGRAY
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                        }

                        linearLayout{
                            topPadding = dip(8)

                            homeGoalDetails=textView {
                                rightPadding = dip(8)
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                text = "Goals"
                            }
                            awayGoalDetails=textView {
                                gravity = Gravity.RIGHT
                                leftPadding = dip(8)
                            }.lparams(matchParent, wrapContent, 1f)
                        }


                        linearLayout {
                            view {
                                backgroundColor = Color.LTGRAY
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                        }

                        textView {
                            topPadding = dip(8)
                            gravity = Gravity.CENTER
                            textSize = 18f
                            setTypeface(null, Typeface.BOLD)
                            text = "Lineups"
                        }

                        linearLayout {
                            topPadding = dip(8)

                            homeGoalKeeper=textView {
                                rightPadding = dip(8)
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                text = "GoalKeeper"
                            }

                            awayGoalKeeper=textView {
                                gravity = Gravity.RIGHT
                                leftPadding = dip(8)

                            }.lparams(matchParent, wrapContent, 1f)
                        }
                        linearLayout {
                            topPadding = dip(8)

                            homeDefense=textView {
                                rightPadding = dip(8)

                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                text = "Defense"
                            }

                            awayDefense=textView {
                                gravity = Gravity.RIGHT
                                leftPadding = dip(8)

                            }.lparams(matchParent, wrapContent, 1f)
                        }
                        linearLayout {
                            topPadding = dip(8)

                            homeMidfield=textView {
                                rightPadding = dip(8)

                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                text = "Midfield"
                            }

                            awayMidfield=textView {
                                gravity = Gravity.RIGHT
                                leftPadding = dip(8)

                            }.lparams(matchParent, wrapContent, 1f)
                        }
                        linearLayout {
                            topPadding = dip(8)

                            homeForward=textView {
                                rightPadding = dip(8)

                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                text = "Forward"
                            }

                            awayForward=textView {
                                gravity = Gravity.RIGHT
                                leftPadding = dip(8)

                            }.lparams(matchParent, wrapContent, 1f)
                        }
                        linearLayout {
                            topPadding = dip(8)

                            homeSubtitutes=textView {
                                rightPadding = dip(8)

                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                text = "Subtitutes"
                            }

                            awaySubtitutes=textView {
                                gravity = Gravity.RIGHT
                                leftPadding = dip(8)
                            }.lparams(matchParent, wrapContent, 1f)
                        }
                    }
                }
            }

            progressBar=progressBar{
            }.lparams{
                centerInParent()
            }
        }

        favoriteState()
        val request= ApiRepo()
        val gson = Gson()
        presenter= MatchDetailPresenter(this,request,gson)
        presenter.getMatchDetails(idEvent)
        presenter.getTeamBadge(idHome,idAway)

        swipeRefresh.onRefresh {
            presenter.getMatchDetails(idEvent)
        }
    }

    private fun favoriteState(){
        database.use{
            val result = select(MatchFavorite.TABLE_FAVORITES)
                .whereArgs("(ID_EVENT = {id})",
                    "id" to idEvent)
            val favorite = result.parseList(classParser<MatchFavorite>())
            if(!favorite.isEmpty())isFavorite=true
        }
    }

    override fun showLoading() {
        progressBar.visible()
        scrollView.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        scrollView.visible()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.match_detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                finish()
                true

            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite=!isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try{
            database.use{
                insert(MatchFavorite.TABLE_FAVORITES,
                    MatchFavorite.ID_EVENT to match.idEvent,
                    MatchFavorite.DATE to match.dateEvent,
                    //home
                    MatchFavorite.HOME_ID to match.idHomeTeam,
                    MatchFavorite.HOME_TEAM to match.strHomeTeam,
                    MatchFavorite.HOME_SCORE to match.intHomeScore,
                    /*MatchFavorite.HOME_GOAL_DETAILS to match.strHomeGoalDetails,
                    MatchFavorite.HOME_LINEUP_GOALKEEPER to match.strHomeLineupGoalkeeper,
                    MatchFavorite.HOME_LINEUP_DEFENSE to match.strHomeLineupDefense,
                    MatchFavorite.HOME_LINEUP_MIDFIELD to match.strHomeLineupMidfield,
                    MatchFavorite.HOME_LINEUP_FORWARD to match.strHomeLineupForward,
                    MatchFavorite.HOME_LINEUP_SUBSTITUTES to match.strHomeLineupSubstitutes,
                    */
                    //away
                    MatchFavorite.AWAY_ID to match.idAwayTeam,
                    MatchFavorite.AWAY_TEAM to match.strAwayTeam,
                    MatchFavorite.AWAY_SCORE to match.intAwayScore
                    /*MatchFavorite.AWAY_GOAL_DETAILS to match.strAwayGoalDetails,
                    MatchFavorite.AWAY_LINEUP_GOALKEEPER to match.strAwayLineupGoalkeeper,
                    MatchFavorite.AWAY_LINEUP_DEFENSE to match.strAwayLineupDefense,
                    MatchFavorite.AWAY_LINEUP_MIDFIELD to match.strAwayLineupMidfield,
                    MatchFavorite.AWAY_LINEUP_FORWARD to match.strAwayLineupForward,
                    MatchFavorite.AWAY_LINEUP_SUBSTITUTES to match.strAwayLineupSubstitutes*/
                    )
            }
            swipeRefresh.snackbar("Added to Favorite").show()
        }catch (e : SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try{
            database.use {
                delete(MatchFavorite.TABLE_FAVORITES,
                    "(ID_EVENT = {id})",
                    "id" to idEvent)
            }
            swipeRefresh.snackbar("Removed from Favorite").show()

        }catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if(isFavorite)
            menuItem?.getItem(0)?.icon=ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon=ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }




    override fun showMatchDetail(data: List<MatchEvents>) {
        match= MatchEvents(data[0].idEvent,
            data[0].dateEvent,
            //home
            data[0].idHomeTeam,
            data[0].strHomeTeam,
            data[0].intHomeScore,
            data[0].strHomeGoalDetails,
            data[0].strHomeLineupGoalkeeper,
            data[0].strHomeLineupSubstitutes,
            data[0].strHomeLineupForward,
            data[0].strHomeLineupMidfield,
            data[0].strHomeLineupDefense,

            //away
            data[0].idAwayTeam,
            data[0].strAwayTeam,
            data[0].intAwayScore,
            data[0].strAwayGoalDetails,
            data[0].strAwayLineupGoalkeeper,
            data[0].strAwayLineupSubstitutes,
            data[0].strAwayLineupForward,
            data[0].strAwayLineupMidfield,
            data[0].strAwayLineupDefense

        )
        swipeRefresh.isRefreshing=false

        dateEvent.text=data[0].dateEvent?.let { DateFormat.getLongDate(it) }
        //home
        homeName.text=data[0].strHomeTeam?.replace(";", "\n")
        homeScore.text=data[0].intHomeScore?.replace(";", "\n")
        homeGoalDetails.text=data[0].strHomeGoalDetails?.replace(";", "\n")
        homeGoalKeeper.text=data[0].strHomeLineupGoalkeeper?.replace(";", "\n")
        homeSubtitutes.text=data[0].strHomeLineupSubstitutes?.replace(";", "\n")
        homeForward.text=data[0].strHomeLineupForward?.replace(";", "\n")
        homeMidfield.text=data[0].strHomeLineupMidfield?.replace(";", "\n")
        homeDefense.text=data[0].strHomeLineupDefense?.replace(";", "\n")

        //away
        awayName.text=data[0].strAwayTeam?.replace(";", "\n")
        awayScore.text=data[0].intAwayScore?.replace(";", "\n")
        awayGoalDetails.text=data[0].strAwayGoalDetails?.replace(";", "\n")
        awayGoalKeeper.text=data[0].strAwayLineupGoalkeeper?.replace(";", "\n")
        awaySubtitutes.text=data[0].strAwayLineupSubstitutes?.replace(";", "\n")
        awayForward.text=data[0].strAwayLineupForward?.replace(";", "\n")
        awayMidfield.text=data[0].strHomeLineupMidfield?.replace(";", "\n")
        awayDefense.text=data[0].strAwayLineupDefense?.replace(";", "\n")
    }
    override fun showBandgeTeam(dataHome: List<TeamBadge>, dataAway: List<TeamBadge>) {
        Picasso.get()
            .load(dataHome[0].teamBadge).into(homeBadge)
        Picasso.get()
            .load(dataAway[0].teamBadge).into(awayBadge)
    }



}