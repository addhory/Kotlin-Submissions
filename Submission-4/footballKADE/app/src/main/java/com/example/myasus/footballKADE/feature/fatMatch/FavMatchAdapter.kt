package com.example.myasus.footballKADE.feature.fatMatch

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.db.MatchFavorite
import com.example.myasus.footballKADE.utils.DateFormat
import org.jetbrains.anko.*

class FavMatchAdapter(
    private val favorite: List<MatchFavorite>,
    private val listener: (MatchFavorite) -> Unit
)  : RecyclerView.Adapter<FavoriteViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FavItemUI()
            .createView(AnkoContext.create(p0.context,p0)))
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(p0: FavoriteViewHolder, p1: Int) {
        p0.bind(favorite[p1], listener)
    }

}

class FavItemUI : AnkoComponent<ViewGroup> {
    @SuppressLint("SetTextI18n")
    override fun createView(ui: AnkoContext<ViewGroup>) : View {
        return with(ui){
            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL

                linearLayout {
                    backgroundColor = Color.WHITE
                    orientation = LinearLayout.VERTICAL
                    padding = dip(8)

                    textView {
                        id = R.id.date_event
                        text = "tanggal"
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        setTypeface(null, Typeface.BOLD)
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, wrapContent)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        textView {
                            id = R.id.home_name
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = "home"
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL

                            textView {
                                id = R.id.home_score
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, Typeface.BOLD)
                                text = "0"
                            }

                            textView {
                                text = "vs"
                            }

                            textView {
                                id = R.id.away_score
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, Typeface.BOLD)
                                text = "0"
                            }
                        }

                        textView {
                            id = R.id.away_name
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = "away"
                        }.lparams(matchParent, wrapContent, 1f)
                    }
                }.lparams(matchParent, matchParent) {
                    setMargins(dip(16), dip(8), dip(16), dip(8))
                }
            }
        }
    }
}

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val eventDate: TextView = itemView.find(R.id.date_event)
    private val homeName : TextView = itemView.find(R.id.home_name)
    private val homeScore : TextView = itemView.find(R.id.home_score)
    private val awayName : TextView = itemView.find(R.id.away_name)
    private val awayScore: TextView = itemView.find(R.id.away_score)

    fun bind (matchFavorite: MatchFavorite, listener: (MatchFavorite) -> Unit){
        if (matchFavorite.intAwayScore==null){
            eventDate.setTextColor(eventDate.context.resources.getColor(R.color.colorAccent))
        }
        eventDate.text = DateFormat.getLongDate(matchFavorite.dateEvent)

        homeName.text=matchFavorite.strHomeTeam
        homeScore.text=matchFavorite.intHomeScore

        awayName.text=matchFavorite.strAwayTeam
        awayScore.text=matchFavorite.intAwayScore

        itemView.setOnClickListener { listener(matchFavorite) }
    }
}
