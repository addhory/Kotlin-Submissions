package com.example.myasus.footballKADE.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.entity.MatchEvents
import com.example.myasus.footballKADE.utils.DateFormat
import com.example.myasus.footballKADE.utils.toGMTFormat
import org.jetbrains.anko.*
import java.text.SimpleDateFormat

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
                    textView {
                        id = R.id.date_time
                        text = "time"
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
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
    private val eventTime : TextView= itemView.find(R.id.date_time)
    private val homeName : TextView = itemView.find(R.id.home_name)
    private val homeScore : TextView = itemView.find(R.id.home_score)
    private val awayName : TextView = itemView.find(R.id.away_name)
    private val awayScore: TextView = itemView.find(R.id.away_score)

    fun bind (match: MatchEvents, listener: (MatchEvents) -> Unit){

        if (match.intHomeScore==null){
            homeName.text=match.strHomeTeam
            awayName.text=match.strAwayTeam

            homeScore.text="-"
            awayScore.text="-"

            eventDate.setTextColor(eventDate.context.resources.getColor(R.color.colorAccent))
            eventTime.setTextColor(eventTime.context.resources.getColor(R.color.colorAccent))
        }else{
            homeName.text=match.strHomeTeam
            homeScore.text=match.intHomeScore
            awayName.text=match.strAwayTeam
            awayScore.text=match.intAwayScore
            eventDate.setTextColor(eventDate.context.resources.getColor(R.color.colorPrimary))
            eventTime.setTextColor(eventTime.context.resources.getColor(R.color.colorPrimary))
        }

        if (match.strTime!=null){
            val dateTime = toGMTFormat(match.dateEvent, match.strTime)

            eventTime.text= SimpleDateFormat("HH:mm").format(dateTime)
            val date = SimpleDateFormat("yyyy-MM-dd").format(dateTime)
            eventDate.text = DateFormat.getLongDate(date)
            Log.d("dateTime", "DateTime adalah " + eventTime.text +" "+ eventDate.text)
        }else{
            eventDate.text=match.dateEvent?.let { DateFormat.getLongDate(it) }
            eventTime.text="-"
        }
        itemView.setOnClickListener { listener(match) }
    }
}