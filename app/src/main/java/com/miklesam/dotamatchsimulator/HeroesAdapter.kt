package com.miklesam.dotamatchsimulator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.datamodels.Hero

class HeroesAdapter(val heroListener: OnHeroListener) : RecyclerView.Adapter<HeroHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.hero_item,parent,false)
        return HeroHolder(itemView,heroListener)
    }

    override fun getItemCount(): Int {
        return Heroes.values().size
    }

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        val hero = Heroes.values()[position]
        holder.heroName.text=hero.heroName
        holder.heroIcon.setImageResource(hero.image_pick)
    }

}