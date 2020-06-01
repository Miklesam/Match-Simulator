package com.miklesam.dotamatchsimulator.game

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.miklesam.dotamanager.dialogs.EndMatchDialog
import com.miklesam.dotamanager.dialogs.LineningDialog
import com.miklesam.dotamanager.ui.game.GameViewModel
import com.miklesam.dotamatchsimulator.GameSimulationView
import com.miklesam.dotamatchsimulator.Heroes
import com.miklesam.dotamatchsimulator.R
import kotlinx.android.synthetic.main.fragment_game.*

class FragmentGame(myListener: backToLobby) : Fragment(R.layout.fragment_game),
    LineningDialog.NoticeDialogListener, EndMatchDialog.toLobbyInterface {
    private var mListener: backToLobby = myListener
    private val radiantImages =
        arrayOfNulls<ImageView>(5)
    private val radiantHeroName =
        arrayOfNulls<TextView>(5)
    private val radiantPlayerName =
        arrayOfNulls<TextView>(5)

    private val direImages =
        arrayOfNulls<ImageView>(5)
    private val direHeroName =
        arrayOfNulls<TextView>(5)
    private var timer: CountDownTimer? = null
    var player: MediaPlayer? = null

    interface backToLobby {
        fun backToLobbyCLicked()
    }

    private var gameEnd = false
    override fun onDialogPositiveClick(position: Array<Int>) {
        gameGame?.CalcilateSpeed(
            arrayOf(
                position[0],
                position[1],
                position[2],
                position[3],
                position[4],
                3,
                3,
                3,
                3,
                3
            )
        )
        timer = object : CountDownTimer(2300, 100) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                player?.start()
                gameViewModel.calculateLineAssign(
                    arrayOf(
                        position[0],
                        position[1],
                        position[2],
                        position[3],
                        position[4],
                        3,
                        3,
                        3,
                        3,
                        3
                    )
                )
            }
        }
        timer?.start()



        timer = object : CountDownTimer(8000, 100) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                nextStage()
            }
        }
        timer?.start()


    }

    var gameGame: GameSimulationView? = null

    lateinit var soundPull: SoundPool
    var soundOne: Int = 0
    var soundTwo: Int = 0
    val TAG = "FragmentGame"
    var heroes: ArrayList<Int>? = null
    var direHeroes: ArrayList<Int>? = null
    var firstInit = true
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            //heroes = requireArguments().getIntegerArrayList(("radiant"))
            //direHeroes = requireArguments().getIntegerArrayList(("dire"))
        }
        heroes = arrayListOf(0, 1, 2, 3, 4)
        direHeroes = arrayListOf(5, 6, 7, 8, 9)

        Log.w(TAG, "onCreate")
        val audioAtributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPull = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAtributes)
            .build()
        soundOne = soundPull.load(context, R.raw.prepare_for_battle, 1)
        soundTwo = soundPull.load(context, R.raw.the_battle_beggins, 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w(TAG, "onViewCreated")
        player = MediaPlayer.create(context, R.raw.battle_10_sec)
        //player?.setOnCompletionListener { player?.start() }
        player?.start()
        player?.pause()
        radiantImages[0] = firstRadiantPlayerHeroImage
        radiantImages[1] = secondRadiantPlayerHeroImage
        radiantImages[2] = thirdRadiantPlayerHeroImage
        radiantImages[3] = forthRadiantPlayerHeroImage
        radiantImages[4] = fifthRadiantPlayerHeroImage
        radiantHeroName[0] = heroRad1
        radiantHeroName[1] = heroRad2
        radiantHeroName[2] = heroRad3
        radiantHeroName[3] = heroRad4
        radiantHeroName[4] = heroRad5
        radiantPlayerName[0] = firstRadiantPlayerName
        radiantPlayerName[1] = secondRadiantPlayerName
        radiantPlayerName[2] = thirdRadiantPlayerName
        radiantPlayerName[3] = forthRadiantPlayerName
        radiantPlayerName[4] = fifthRadiantPlayerName


        direImages[0] = firstDirePlayerHeroImage
        direImages[1] = secondDirePlayerHeroImage
        direImages[2] = thirdDirePlayerHeroImage
        direImages[3] = forthDirePlayerHeroImage
        direImages[4] = fifthDirePlayerHeroImage

        direHeroName[0] = heroDire1
        direHeroName[1] = heroDire2
        direHeroName[2] = heroDire3
        direHeroName[3] = heroDire4
        direHeroName[4] = heroDire5


        for (i in 0 until 5) {
            radiantImages[i]?.setImageResource(
                Heroes.values().find { it.id == heroes?.get(i) ?: 0 }!!.icon
            )
            radiantHeroName[i]?.text =
                Heroes.values().find { it.id == heroes?.get(i) ?: 0 }!!.heroName

            direImages[i]?.setImageResource(
                Heroes.values().find { it.id == direHeroes?.get(i) ?: 0 }!!.icon
            )

            direHeroName[i]?.text =
                Heroes.values().find { it.id == direHeroes?.get(i) ?: 0 }!!.heroName
        }

        gameGame = view.findViewById(R.id.gameGame)
        heroes?.let { direHeroes?.let { it1 -> gameGame?.initHeroes(it, it1) } }
        gameGame?.Start()
        val timerAssignLine = object : CountDownTimer(2000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                gameGame?.setBasePosition()
            }

            override fun onFinish() {
                //soundPull.play(soundOne, 1F, 1F, 0, 0, 1F)
                CreateDeskDialog()
            }
        }
        timerAssignLine.start()


        gameViewModel.getPlayersMatchStatistic().observe(viewLifecycleOwner, Observer {
            Log.w("FragmentGame", it.toString())
            radiantStat1.text = it[0]
            radiantStat2.text = it[1]
            radiantStat3.text = it[2]
            radiantStat4.text = it[3]
            radiantStat5.text = it[4]

            direStat1.text = it[5]
            direStat2.text = it[6]
            direStat3.text = it[7]
            direStat4.text = it[8]
            direStat5.text = it[9]

            radiantTotalScore.text = it[10]
            direTotalScore.text = it[11]

        })

        gameViewModel.getradiantTowers().observe(viewLifecycleOwner, Observer {
            Log.w("Fragment Game", "Current TowerState= $it")
            gameGame?.setTowers(it)
            gameEnd = !it[9] || !it[19]
            if (!it[9] && !it[19]) {
                initiateEnd(3)
            } else if (!it[9]) {
                initiateEnd(2)
            } else if ((!it[19])) {
                initiateEnd(1)
            }

        })

    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.w(TAG, "onActivityCreated")

    }

    private fun CreateDeskDialog() {
        soundPull.play(soundOne, 1F, 1F, 0, 0, 1F)
        player?.pause()
        val dialog =
            LineningDialog(this, heroes)
        fragmentManager?.let { dialog.show(it, "CreateDeskDialog") }
    }


    override fun onStart() {
        super.onStart()
        Log.w(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        player?.stop()
        player?.release()
        player = null
    }

    override fun onDestroyView() {
        gameGame = null
        timer = null
        player = null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        Log.w(TAG, "onResume")
    }

    fun nextStage() {
        //gameViewModel.setStats(9)
        //soundPull.play(soundTwo, 1F, 1F, 0, 0, 1F)
        if (!gameEnd) {
            CreateDeskDialog()
        }
    }

    fun initiateEnd(side: Int) {
        if (firstInit) {
            Log.w("Initiate End", "End")
            firstInit = false
            gameGame?.initiateWin(side)
            CreateEndMatchDialogDialog(side)
        }
    }

    private fun CreateEndMatchDialogDialog(side: Int) {
        player?.stop()
        val dialog = EndMatchDialog(this, side)
        fragmentManager?.let { dialog.show(it, "CreateEndMatchDialogDialog") }
    }

    override fun goToLobbyClick() {
        Log.w("FragmentGame", "EndGameClicked")
        mListener.backToLobbyCLicked()
    }

}