package com.example.myasus.myapplicationkotl

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.myasus.myapplicationkotl.R.id.image
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list.view.*
import org.jetbrains.anko.AnkoContext

class RecyclerViewAdapter(list: MutableList<Item>, private val listener: (Item) -> Unit)
    : androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var mutableList: MutableList<Item> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUI().createView(AnkoContext.create(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(mutableList[position], listener)
    }

    override fun getItemCount(): Int {
      return mutableList.size
    }

    class ViewHolder(itemView: View?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!){
        fun bindItem(
            items: Item,
            listener: (Item) -> Unit
        ) = with (itemView) {
            itemView.name.text=items.name
            items.image?.let { Picasso.get().load(it).into(image) }
            itemView.setOnClickListener{
                listener(items)
            }
        }
    }
}




