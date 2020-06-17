package com.miklesam.dotamatchsimulator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miklesam.dotamatchsimulator.game.FragmentGame
import com.miklesam.dotamatchsimulator.simplefragments.*

class MainActivity : AppCompatActivity(), FragmentMenu.MenuListener, PickStage.nextFromPick,
    FragmentGame.backToLobby, FragmentInfo.InfoListener {
    private lateinit var mainVM: MultiActivityVM
    var progressState = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainVM = ViewModelProviders.of(this).get(MultiActivityVM::class.java)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        if (savedInstanceState == null) {
            showFragmentMain()
        }
        mainVM.getProgress().observe(this, Observer {
            progressState = it
        })
    }


    private fun showFragmentMain() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment =
            FragmentMenu()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    override fun gameClicked() {
        val transaction = supportFragmentManager.beginTransaction()
        //val fragment = FragmentGame()
        val fragment = PickStage()
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    override fun multipleerClicked() {
        val intent = Intent(this, MultipleerActivity::class.java)
        startActivity(intent)
    }

    override fun infoClicked() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment =
            FragmentInfo()
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    override fun pickEnded(radiant: ArrayList<Int>, direPicks: ArrayList<Int>) {
        mainVM.setProgress(1)
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentGame()
        val bundle = Bundle()
        bundle.putIntegerArrayList("radiant", radiant)
        bundle.putIntegerArrayList("dire", direPicks)
        fragment.arguments = bundle
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    override fun backToLobbyCLicked() {
        mainVM.setProgress(0)
        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
    }

    override fun howToPlayClicked() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment =
            FragmentHowToPlay()
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    override fun statsClicked() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment =
            FragmentStats()
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    override fun privacyPolicyClicked() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment =
            FragmentPrivacyPolicy()
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (progressState == 0) {
            super.onBackPressed()
        }
    }


}
