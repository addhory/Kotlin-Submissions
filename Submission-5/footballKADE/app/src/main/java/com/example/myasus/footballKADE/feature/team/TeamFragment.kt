package com.example.myasus.footballKADE.feature.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.adapter.TeamAdapter
import com.example.myasus.footballKADE.entity.TeamItem
import com.example.myasus.footballKADE.entity.api.ApiRepo
import com.example.myasus.footballKADE.feature.detailteam.DetailTeamActivity
import com.example.myasus.footballKADE.utils.gone
import com.example.myasus.footballKADE.utils.invisible
import com.example.myasus.footballKADE.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.support.v4.toast

class TeamFragment : Fragment(), TeamView{

    private var teams : MutableList<TeamItem> = mutableListOf()

    private lateinit var presenter : TeamPresenter
    private lateinit var leagueName: String
    private lateinit var listTeam : TeamAdapter


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        with(activity as AppCompatActivity){
            setSupportActionBar(toolbar)
        }

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter=spinnerAdapter
        val request = ApiRepo()
        val gson = Gson()

        presenter= TeamPresenter(this, request, gson)

        listTeam = TeamAdapter(teams){
            DetailTeamActivity.start(context, it)
        }
        with(rvTeam){
            adapter=listTeam
            layoutManager=LinearLayoutManager(context)
        }

        spinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.search_menu_view,menu)
        val menuu = menu?.findItem(R.id.menu_search_view)
        val searchView=menuu?.actionView as SearchView
        searchView.queryHint = "Search Team"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0.toString().isEmpty()){
                    spinner_container.visible()
                    presenter.getTeamList(leagueName)

                }else {
                    spinner_container.gone()
                    presenter.getSearchTeam(p0.toString())
                }
                return false
            }
        })

    }


    override fun showLoading() {
        progress_bar.visible()
        rvTeam.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        progress_bar.invisible()
        rvTeam.visibility=View.VISIBLE
    }

    override fun emptyData() {
        toast("No Internet Connection")
    }

    override fun showTeamList(data: MutableList<TeamItem>) {
        teams.clear()
        teams.addAll(data)

        listTeam.notifyDataSetChanged()

    }
}