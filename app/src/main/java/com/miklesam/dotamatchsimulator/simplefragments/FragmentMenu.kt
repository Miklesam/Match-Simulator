package com.miklesam.dotamatchsimulator.simplefragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miklesam.dotamatchsimulator.R
import kotlinx.android.synthetic.main.fragment_menu.*


class FragmentMenu :Fragment(R.layout.fragment_menu){

    interface MenuListener {
        fun gameClicked()
        fun multipleerClicked()
        fun infoClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuListener = activity as MenuListener
        game_bttn.setOnClickListener { menuListener.gameClicked() }
        multipleer_bttn.setOnClickListener { menuListener.multipleerClicked() }
        info_bttn.setOnClickListener { menuListener.infoClicked() }
    }
}