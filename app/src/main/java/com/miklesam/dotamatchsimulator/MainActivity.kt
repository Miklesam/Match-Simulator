package com.miklesam.dotamatchsimulator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.games.AchievementsClient
import com.google.android.gms.games.Games
import com.google.android.gms.games.LeaderboardsClient
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
        initGoogleClientAndSignin()
        mainVM.getProgress().observe(this, Observer {
            progressState = it
        })
    }


    private var googleSignInClient: GoogleSignInClient? = null
    private var achievementClient: AchievementsClient? = null
    private var leaderboardsClient: LeaderboardsClient? = null

    fun initGoogleClientAndSignin() {
        Log.w("Activity", "try to  init")
        googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN
            ).build()
        )

        googleSignInClient?.silentSignIn()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.w("Activity", "succes Sign")
                achievementClient = Games.getAchievementsClient(
                    this,
                    task.result!!
                )
                leaderboardsClient = Games.getLeaderboardsClient(
                    this,
                    task.result!!
                )
            } else {
                Log.e("Error", "signInError", task.exception)
            }
        }
    }


    private fun showFragmentMain() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment =
            FragmentMenu()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    override fun gameClicked() {
        achievementClient?.unlock(getString(R.string.achieve_1))
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
        achievementClient?.unlock(getString(R.string.achieve_3))
        val transaction = supportFragmentManager.beginTransaction()
        val fragment =
            FragmentHowToPlay()
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    override fun statsClicked() {
        achievementClient?.unlock(getString(R.string.achieve_4))
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

    override fun achievmentsClicked() {
        showAchievements()
    }

    override fun aboutClicked() {
        achievementClient?.unlock(getString(R.string.achieve_5))
        val transaction = supportFragmentManager.beginTransaction()
        val fragment =
            FragmentAbout()
        transaction.replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (progressState == 0) {
            super.onBackPressed()
        }
    }

    private fun showAchievements() {
        achievementClient?.achievementsIntent?.addOnSuccessListener { intent ->
            Log.w("Activity", "start Activity")
            startActivityForResult(intent, 0)
        }
    }


}
