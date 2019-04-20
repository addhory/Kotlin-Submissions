package com.example.myasus.kotlin_sub3.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myasus.kotlin_sub3.R
import com.example.myasus.kotlin_sub3.entity.MatchEvents
import com.example.myasus.kotlin_sub3.utils.DateFormat
import org.jetbrains.anko.*

class MatchAdapter(private val eventList: List<MatchEvents>,
                   private val listener: (MatchEvents) -> Unit) :
    RecyclerView.Adapter<MatchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder{
      return MatchViewHolder(ItemUI().
          createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(p0: MatchViewHolder, p1: Int) {
        p0.bind(eventList[p1],listener)
    }

}

class ItemUI : AnkoComponent<ViewGroup> {
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

class MatchViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {
    private val eventDate: TextView = itemView.find(R.id.date_event)
    private val homeName : TextView = itemView.find(R.id.home_name)
    private val homeScore : TextView = itemView.find(R.id.home_score)
    private val awayName : TextView = itemView.find(R.id.away_name)
    private val awayScore: TextView = itemView.find(R.id.away_score)

    fun bind (match: MatchEvents, listener: (MatchEvents) -> Unit){
        if (match.intHomeScore==null){
            eventDate.setTextColor(eventDate.context.resources.getColor(R.color.colorAccent))
        }
        eventDate.text = DateFormat.getLongDate(match.dateEvent)
        homeName.text=match.strHomeTeam
        homeScore.text=match.intHomeScore
        awayName.text=match.strAwayTeam
        awayScore.text=match.intAwayScore

        itemView.setOnClickListener { listener(match) }
    }
}