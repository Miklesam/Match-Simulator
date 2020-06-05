package com.miklesam.dotamatchsimulator

import androidx.recyclerview.widget.RecyclerView

interface OnHeroListener {
    fun onHeroClick(position: Int,holder:RecyclerView.ViewHolder)
}