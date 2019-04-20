package com.example.myasus.footballKADE.feature.detailteam.player

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.adapter.PlayerAdapter
import com.example.myasus.footballKADE.entity.PlayerItem
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.feature.detailplayer.DetailPlayerActivity
import com.example.myasus.footballKADE.utils.invisible
import com.example.myasus.footballKADE.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team_player.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.support.v4.toast

class PlayerFragment : Fragment(), PlayerView{


    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun newInstance(args: String): PlayerFragment {
            val fragment = PlayerFragment()
            fragment.arguments = bundleOf(EXTRA_PARAM to args)

            return fragment
        }
    }

    private lateinit var presenter : PlayerPresenter
    private var player : MutableList<PlayerItem> = mutableListOf()
    private lateinit var listAdapter: PlayerAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_player, container , false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val request = ApiRepo()
        val gson= Gson()

        presenter=PlayerPresenter(this,request,gson)
        listAdapter = PlayerAdapter(player){
            DetailPlayerActivity.start(context,it)

        }
        with(recycler_view){
            adapter=listAdapter
            layoutManager=LinearLayoutManager(context)
        }
        presenter.getPlayerList(arguments?.getString(EXTRA_PARAM).toString())

    }

    override fun showLoading() {
        progress_bar.visible()
        recycler_view.invisible()
    }
    override fun emptyData() {
        recycler_view.invisible()
        progress_bar.invisible()
        toast("No Internet Connection")

    }
    override fun hideLoading() {
        progress_bar.invisible()
        recycler_view.visible()
    }

    override fun showPlayerList(data: List<PlayerItem>) {
        player.clear()
        player.addAll(data)
        listAdapter.notifyDataSetChanged()

    }
}