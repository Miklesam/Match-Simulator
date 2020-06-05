package com.miklesam.dotamatchsimulator

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.hero_item.view.*

class HeroHolder (itemView:View, var heroListener: OnHeroListener):RecyclerView.ViewHolder(itemView),View.OnClickListener{
    override fun onClick(p0: View?) {
        heroListener.onHeroClick((adapterPosition),this)
    }
    val heroIcon :ImageView
    val heroName:TextView
    init{
        heroIcon=itemView.heroIcon
        heroName=itemView.heroName

        itemView.setOnClickListener(this)
    }
}