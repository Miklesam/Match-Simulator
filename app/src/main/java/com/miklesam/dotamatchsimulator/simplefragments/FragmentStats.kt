package com.miklesam.dotamatchsimulator.simplefragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamatchsimulator.R
import com.miklesam.dotamatchsimulator.adapters.HeroesAdapter
import com.miklesam.dotamatchsimulator.adapters.OnHeroListener
import kotlinx.android.synthetic.main.fragment_stats.*


class FragmentStats :Fragment(R.layout.fragment_stats),
    OnHeroListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerHeroes.layoutManager = LinearLayoutManager(context)
        recyclerHeroes?.setHasFixedSize(true)
        val adapter =
            HeroesAdapter(this)
        recyclerHeroes?.adapter = adapter
    }

    override fun onHeroClick(position: Int, holder: RecyclerView.ViewHolder) {
        //
    }
}