package com.example.myasus.footballKADE.feature.detailplayer

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.myasus.footballKADE.R
import com.example.myasus.footballKADE.entity.PlayerItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.player_detail_activity.*
import org.jetbrains.anko.startActivity

class DetailPlayerActivity : AppCompatActivity(){

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun start(context: Context?, playerItem: PlayerItem) {
            context?.startActivity<DetailPlayerActivity>(EXTRA_PARAM to playerItem)
        }
    }
    private lateinit var playerItem : PlayerItem
    private lateinit var idPlayer : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_detail_activity)

        playerItem=intent.getParcelableExtra(EXTRA_PARAM)

        supportActionBar?.title=playerItem.strPlayer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        showPlayerDetail()
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


   fun showPlayerDetail() {
        Picasso.get().load(playerItem.strFanart1).into(iv_fanart)
        tv_height.text = playerItem.strHeight
        tv_weight.text=playerItem.strWeight
        tv_description.text=playerItem.strDescriptionEN
        tv_position.text=playerItem.strPosition
        supportActionBar?.title=playerItem.strPlayer
    }
}