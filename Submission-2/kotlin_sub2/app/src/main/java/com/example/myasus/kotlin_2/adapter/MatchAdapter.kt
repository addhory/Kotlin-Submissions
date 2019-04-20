package com.example.myasus.kotlin_2.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myasus.kotlin_2.utils.DateFormat
import com.example.myasus.kotlin_2.R
import com.example.myasus.kotlin_2.detail.DetailActivity
import com.example.myasus.kotlin_2.entity.matchEvent
import kotlinx.android.synthetic.main.item_match.view.*
import org.jetbrains.anko.startActivity

class MatchAdapter(val eventList: List<matchEvent>, val context: Context?) :
    RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match, parent, false))
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(p0: MatchViewHolder, p1: Int) {
        p0.bind(eventList[p1])
    }


    class MatchViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {
        fun bind (match: matchEvent){
            if (match.intHomeScore == null){
                itemView.dateTv.setTextColor(itemView.context.resources.getColor(R.color.colorPrimary))
            }
            itemView.dateTv.text= match.dateEvent?.let { DateFormat.formatDateToMatch(it) }
            itemView.homeNameTv.text = match.strHomeTeam
            itemView.homeScoreTv.text = match.intHomeScore
            itemView.awayNameTv.text= match.strAwayTeam
            itemView.awayScoreTv.text=match.intAwayScore

            itemView.setOnClickListener{
                itemView.context.startActivity<DetailActivity>("match" to match)

            }

        }
    }

}