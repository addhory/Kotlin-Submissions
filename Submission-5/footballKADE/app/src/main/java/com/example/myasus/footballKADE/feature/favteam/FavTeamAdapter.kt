package com.example.myasus.footballKADE.feature.favteam

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.db.TeamFavorite
import com.example.myasus.footballKADE.entity.TeamItem
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class FavTeamAdapter(private val favList: List<TeamItem>, private val listener: (TeamItem) -> Unit)
    : RecyclerView.Adapter<FavTeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavTeamViewHolder {
        return FavTeamViewHolder(FavTeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavTeamViewHolder, position: Int) {
        holder.bindItem(favList[position], listener)
    }

    override fun getItemCount(): Int = favList.size

}

class FavTeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.team_badge
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.team_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }

            }
        }
    }

}
class FavTeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val teamBadge: ImageView = view.find(R.id.team_badge)
    private val teamName: TextView = view.find(R.id.team_name)

    fun bindItem(favTeam: TeamItem, listener: (TeamItem) -> Unit) {
        Picasso.get().load(favTeam.strTeamBadge).into(teamBadge)
        teamName.text = favTeam.strTeam
        itemView.setOnClickListener { listener(favTeam) }
    }
}