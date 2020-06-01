package com.miklesam.dotamatchsimulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.miklesam.dotamatchsimulator.game.FragmentGame

class MainActivity : AppCompatActivity(), FragmentMenu.MenuListener, PickStage.nextFromPick,
    FragmentGame.backToLobby {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        if (savedInstanceState == null) {
            showFragmentMain()
        }
    }


    private fun showFragmentMain() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentMenu()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    override fun gameClicked() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentGame(this)
        //val fragment = PickStage()
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    override fun pickEnded(radiant: ArrayList<Int>, direPicks: ArrayList<Int>) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentGame(this)
        val bundle = Bundle()
        bundle.putIntegerArrayList("radiant", radiant)
        bundle.putIntegerArrayList("dire", direPicks)
        fragment.arguments = bundle
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    override fun backToLobbyCLicked() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
    }


}
