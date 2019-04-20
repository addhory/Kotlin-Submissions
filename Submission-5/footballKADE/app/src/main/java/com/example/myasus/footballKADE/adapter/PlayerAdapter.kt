package com.example.myasus.footballKADE.adapter

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.entity.PlayerItem
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class PlayerAdapter(
    private val player: List<PlayerItem>,
    private val listener: (PlayerItem) -> Unit
): RecyclerView.Adapter<PlayerViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().
            createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount():Int = player.size

    override fun onBindViewHolder(p0: PlayerViewHolder, p1: Int) {
        p0.bind(player[p1], listener)
    }

}

class PlayerViewHolder(itemView : View): RecyclerView.ViewHolder (itemView){
    private val playerImg: ImageView = itemView.find(R.id.img_player)
    private val playerName : TextView = itemView.find(R.id.player_name)
    private val playerPos : TextView = itemView.find(R.id.player_pos)

    fun bind(player: PlayerItem, listener: (PlayerItem) -> Unit){
        Picasso.get().load(player.strCutout).into(playerImg)
        playerName.text=player.strPlayer
        playerPos.text=player.strPosition

        itemView.setOnClickListener { listener(player) }
    }

}

class PlayerUI: AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            lparams(matchParent, wrapContent)
            orientation = LinearLayout.VERTICAL

            linearLayout {
                gravity = Gravity.CENTER_VERTICAL
                padding = dip(16)

                linearLayout {
                    gravity = Gravity.CENTER_VERTICAL

                    imageView {
                        id = R.id.img_player
                    }.lparams(dip(50), dip(50))

                    textView {
                        id = R.id.player_name
                    }.lparams(wrapContent, wrapContent) {
                        leftMargin = dip(16)
                    }
                }.lparams(matchParent, wrapContent, 1f)

                textView {
                    id = R.id.player_pos
                    gravity = Gravity.END
                }.lparams(matchParent, wrapContent, 1f)
            }
        }
    }
}
