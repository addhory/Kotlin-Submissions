package com.example.myasus.myapplicationkotl

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myasus.myapplicationkotl.R.id.image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*
import org.jetbrains.anko.*

class DetailActivity : AppCompatActivity (){
    private lateinit var item: Item
    private lateinit var imageClub : ImageView
    private lateinit var namaClub: TextView
    private lateinit var descClub: TextView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        verticalLayout{
            padding=dip(10)
            gravity= Gravity.CENTER_HORIZONTAL

            imageClub = imageView().lparams{
                width=dip(100)
                height=dip(100)
            }

            namaClub=textView().lparams{
                width= wrapContent
                height= wrapContent
            }
            descClub=textView().lparams{
                width= wrapContent
                height= wrapContent
                padding = dip(15)
            }
        }
        val intent = intent
        item=intent.getParcelableExtra("data")
        namaClub.text=item.name
        descClub.text= item.desc
        Glide.with(this).load(item.image).into(imageClub)
    }
}