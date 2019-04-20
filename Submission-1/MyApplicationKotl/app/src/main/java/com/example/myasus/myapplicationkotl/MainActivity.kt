package com.example.myasus.myapplicationkotl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {

    val data = ArrayList<Item>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()

        val adapter = RecyclerViewAdapter(data) { item ->
            startActivity(intentFor<DetailActivity>("data" to item))

        }
        MainActivityUI(adapter).setContentView(this)
    }


    private fun initData(){
        val name = resources.getStringArray(R.array.club_name)
        val image = resources.obtainTypedArray(R.array.club_image)
        val desc = resources.getStringArray(R.array.club_description)
        data.clear()
        for(i in name.indices){
            data.add(Item(name[i], image.getResourceId(i, 0), desc[i]))
        }

        image.recycle()
    }
}
