package com.miklesam.dotamatchsimulator.simplefragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miklesam.dotamatchsimulator.R
import kotlinx.android.synthetic.main.fragment_info.*


class FragmentInfo :Fragment(R.layout.fragment_info){

    interface InfoListener {
        fun howToPlayClicked()
        fun statsClicked()
        fun privacyPolicyClicked()
        fun achievmentsClicked()
        fun aboutClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val infoListener = activity as InfoListener
        how_to_play_bttn.setOnClickListener { infoListener.howToPlayClicked() }
        stats_bttn.setOnClickListener { infoListener.statsClicked() }
        privacy_policy.setOnClickListener { infoListener.privacyPolicyClicked() }
        achivments.setOnClickListener { infoListener.achievmentsClicked() }
        about.setOnClickListener { infoListener.aboutClicked() }
    }
}