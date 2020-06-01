package com.miklesam.dotamatchsimulator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_menu.*


class FragmentMenu :Fragment(R.layout.fragment_menu){

    interface MenuListener {
        fun gameClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuListener = activity as MenuListener
        game_bttn.setOnClickListener { menuListener.gameClicked() }
    }
}