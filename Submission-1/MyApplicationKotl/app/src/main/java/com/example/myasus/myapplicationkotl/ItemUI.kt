package com.example.myasus.myapplicationkotl

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import org.jetbrains.anko.*

class ItemUI : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {

        return linearLayout {
            orientation = LinearLayout.HORIZONTAL
            padding = dip(16)

            imageView {
                id = R.id.image
            }.lparams{
                width = dip(50)
                height = dip(50)
            }


            textView {
                id = R.id.name
            }.lparams {
                gravity = Gravity.CENTER_VERTICAL
                margin = dip(10)
                width = wrapContent
                height = wrapContent
            }
        }
    }
}