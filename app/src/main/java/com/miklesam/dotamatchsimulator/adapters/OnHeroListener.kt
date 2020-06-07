package com.miklesam.dotamatchsimulator.adapters

import androidx.recyclerview.widget.RecyclerView

interface OnHeroListener {
    fun onHeroClick(position: Int,holder:RecyclerView.ViewHolder)
}