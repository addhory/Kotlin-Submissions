package com.example.myasus.footballKADE.feature.detailteam

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.adapter.ViewPagerAdapter
import com.example.myasus.footballKADE.entity.TeamItem
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.feature.detailteam.TeamDesFragment.TeamDesFragment
import com.example.myasus.footballKADE.feature.detailteam.player.PlayerFragment
import com.example.myasus.footballKADE.feature.favteam.FavUtils
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.team_detail_activity.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class DetailTeamActivity : AppCompatActivity(), TeamDetailView {
    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun start(context: Context?, team: TeamItem) {
            context?.startActivity<DetailTeamActivity>(EXTRA_PARAM to team)
        }

    }

    private lateinit var team: TeamItem
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teamFav: TeamItem

    private var menuTeamFavorites: Menu? = null
    private var isFavorite: Boolean = false
    private var favTeamDb = FavUtils()
    private lateinit var idTeam: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_detail_activity)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        team = intent.getParcelableExtra(EXTRA_PARAM)
        idTeam = team.idTeam.toString()

        val bundle = Bundle()
        bundle.putString("id", idTeam)

        initData()
        val request = ApiRepo()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetails(idTeam)

        isFavorite = favTeamDb.isFav(applicationContext, team)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        val teamDesFragment = TeamDesFragment.newInstance(team.strDescriptionEN.toString())
        val playerFragment = PlayerFragment.newInstance(team.strTeam.toString())

        adapter.populateFragment(teamDesFragment, "Description")
        adapter.populateFragment(playerFragment, "Players")
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)

    }

    override fun showLoading() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.team_detail_menu, menu)
        menuTeamFavorites = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()

                true
            }
            R.id.add_to_favorite_team -> {
                if (isFavorite) {
                    favTeamDb.removeFromFav(applicationContext, team)
                    toast("Team removed from Favorite")
                } else {
                    favTeamDb.addToFav(applicationContext, team)
                    toast("Team added to Favorite")
                }
                isFavorite = !isFavorite
                setFavorite()
                true

            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initData() {
        Picasso.get()
            .load(team.strTeamBadge).into(iv_team)
        tv_teamName.text = team.strTeam
        tv_stadium.text = team.strStadium
        tv_year.text = team.intFormedYear
    }

    override fun hideLoading() {
    }

    override fun showTeamDetail(data: List<TeamItem>) {

    }

    override fun emptyData() {
        toast("No Internet Connection")
    }

    private fun setFavorite() {
        if (isFavorite)
            menuTeamFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuTeamFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

}