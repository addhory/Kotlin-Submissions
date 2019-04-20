package com.example.myasus.footballKADE.feature.searchmatch

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.SearchView
import android.view.Menu
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.adapter.MatchAdapter
import com.example.myasus.footballKADE.entity.IdEvent
import com.example.myasus.footballKADE.entity.MatchEvents
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.feature.detailmatch.DetailActivity
import com.example.myasus.footballKADE.utils.invisible
import com.example.myasus.footballKADE.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_match.*

class SearchMatchActivity : AppCompatActivity(), SearchMatchView{

    private lateinit var presenter : SearchMatchPresenter
    private var events : MutableList<MatchEvents> = mutableListOf()
    private lateinit var adapterM: MatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search_match)

        adapterM = MatchAdapter(events){
            val intent = Intent(applicationContext, DetailActivity::class.java)
            intent.putExtra("ids", IdEvent("${it.idEvent}", "${it.idHomeTeam}",
                "${it.idAwayTeam}")
            )
            startActivity(intent)
        }
        with(rvSearchMatch){
            adapter = adapterM
            layoutManager=LinearLayoutManager(context)
        }

        val request = ApiRepo()
        val gson = Gson()
        presenter = SearchMatchPresenter(this,request,gson)

        presenter.getMatch()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu_view, menu)
        val menuu = menu?.findItem(R.id.menu_search_view)
        val searchView=menuu?.actionView as SearchView
        searchView.isIconified=false
        searchView.queryHint = "Search Match"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                presenter.getMatch(p0.toString())
                return false
            }
        })
        return true
    }

    override fun showLoading() {
        progress_bar.visible()
        tv_empty.invisible()
        rvSearchMatch.invisible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
        rvSearchMatch.visible()
        tv_empty.invisible()

    }

    override fun emptyData() {
        progress_bar.invisible()
        rvSearchMatch.invisible()
        tv_empty.visible()

    }

    override fun showMatchList(data: MutableList<MatchEvents>) {
        events.clear()
        events.addAll(data)
        adapterM.notifyDataSetChanged()
        rvSearchMatch.scrollToPosition(0)
    }


}