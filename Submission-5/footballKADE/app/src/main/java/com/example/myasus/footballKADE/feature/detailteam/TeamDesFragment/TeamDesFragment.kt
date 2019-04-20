package com.example.myasus.footballKADE.feature.detailteam.TeamDesFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myasus.footballKADE.R
import kotlinx.android.synthetic.main.fragment_team_des.*
import org.jetbrains.anko.bundleOf

class TeamDesFragment: Fragment(){

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun newInstance(args: String): TeamDesFragment {
            val fragment = TeamDesFragment()
            fragment.arguments = bundleOf(EXTRA_PARAM to args)

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_des,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_overview.text=arguments?.getString(EXTRA_PARAM)


    }
}