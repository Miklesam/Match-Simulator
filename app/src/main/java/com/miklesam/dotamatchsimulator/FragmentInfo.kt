package com.miklesam.dotamatchsimulator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_menu.*


class FragmentInfo :Fragment(R.layout.fragment_info){

    interface InfoListener {
        fun statsClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val infoListener = activity as InfoListener
        stats_bttn.setOnClickListener { infoListener.statsClicked() }

    }
}