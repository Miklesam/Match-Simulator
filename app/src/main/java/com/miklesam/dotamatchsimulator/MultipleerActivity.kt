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
import com.miklesam.dotamatchsimulator.multipleer.MultiGame
import com.miklesam.dotamatchsimulator.multipleer.MultiPick
import com.miklesam.dotamatchsimulator.multipleer.client.FragmentClient
import com.miklesam.dotamatchsimulator.multipleer.host.FragmentHost
import com.miklesam.dotamatchsimulator.simplefragments.FragmentHowToConnect
import com.miklesam.dotamatchsimulator.utils.replaceFragmentFromRightToLeft


class MultipleerActivity : AppCompatActivity(), FragmentMultipleer.MultioleerListener,
    FragmentHost.hostListener, FragmentClient.clientListener,
    MultiPick.nextMultiPick, MultiGame.toMain {
    var callIntent = true
    private lateinit var multiVM: MultiActivityVM
    var progressState = 0
    private var googleSignInClient: GoogleSignInClient? = null
    private var achievementClient: AchievementsClient? = null
    private var leaderboardsClient: LeaderboardsClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multipleer)
        multiVM = ViewModelProviders.of(this).get(MultiActivityVM::class.java)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        if (savedInstanceState == null) {
            showFragmentMultipleer()
        }
        initGoogleClientAndSignin()

        multiVM.getProgress().observe(this, Observer {
            progressState = it
        })
    }


    private fun showFragmentMultipleer() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentMultipleer()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    override fun howToConnectClicked() {
        replaceFragmentFromRightToLeft(FragmentHowToConnect(), true)
    }

    override fun hostClicked() {
        replaceFragmentFromRightToLeft(FragmentHost(), true)
    }

    override fun clientClicked() {
        replaceFragmentFromRightToLeft(FragmentClient(), true)
    }

    override fun hostOk() {
        achievementClient?.unlock(getString(R.string.achieve_2))
        multiVM.setProgress(1)
        replaceFragmentFromRightToLeft(MultiPick(true), true)
    }

    override fun clientOk() {
        achievementClient?.unlock(getString(R.string.achieve_2))
        multiVM.setProgress(1)
        replaceFragmentFromRightToLeft(MultiPick(false), true)
    }

    override fun radiantPickEnded() {
        replaceFragmentFromRightToLeft(MultiGame(true), true)
    }

    override fun direPickEnded() {
        replaceFragmentFromRightToLeft(MultiGame(false), true)
    }

    override fun goToMain() {
        //callIntent=false
        multiVM.setProgress(2)
        finishAffinity()
        val intent = Intent(
            this,
            MainActivity::class.java
        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        if (progressState != 2 && callIntent) {
            finishAffinity()
            val intent = Intent(
                this,
                MainActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (progressState == 0) {
            super.onBackPressed()
            callIntent = false
        }
    }

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
}
