package com.miklesam.dotamanager.ui.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.miklesam.dotamanager.datamodels.HeroStats
import com.miklesam.dotamatchsimulator.datamodels.Heroes
import com.miklesam.dotamatchsimulator.datamodels.Side
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val allPlayersStats = MutableLiveData<List<String>>()
    private val allTowers = MutableLiveData<List<Boolean>>()
    fun getPlayersMatchStatistic(): LiveData<List<String>> = allPlayersStats
    fun getradiantTowers(): LiveData<List<Boolean>> = allTowers
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    var radiantHeroes = ArrayList<Int>()
    var direHeroes = ArrayList<Int>()
    val gameState = MutableLiveData<Int>()
    fun getTimeState(): LiveData<Int> = gameState
    var gameCount = 0

    val RadiantTeam = arrayListOf<HeroStats>(
        HeroStats(0, 0, 0, 1),
        HeroStats(0, 0, 0, 2),
        HeroStats(0, 0, 0, 3),
        HeroStats(0, 0, 0, 4),
        HeroStats(0, 0, 0, 5)
    )
    val DireTeam = arrayListOf<HeroStats>(
        HeroStats(0, 0, 0, 1),
        HeroStats(0, 0, 0, 2),
        HeroStats(0, 0, 0, 3),
        HeroStats(0, 0, 0, 4),
        HeroStats(0, 0, 0, 5)
    )


    val radiantTowers = Side(
        arrayListOf(true, true, true),
        arrayListOf(true, true, true),
        arrayListOf(true, true, true)
    )
    val direTowers = Side(
        arrayListOf(true, true, true),
        arrayListOf(true, true, true),
        arrayListOf(true, true, true)
    )

    private fun assignStats(): List<String> {
        val r1 = "${RadiantTeam[0].kills}/${RadiantTeam[0].death}/${RadiantTeam[0].assist}"
        val r2 = "${RadiantTeam[1].kills}/${RadiantTeam[1].death}/${RadiantTeam[1].assist}"
        val r3 = "${RadiantTeam[2].kills}/${RadiantTeam[2].death}/${RadiantTeam[2].assist}"
        val r4 = "${RadiantTeam[3].kills}/${RadiantTeam[3].death}/${RadiantTeam[3].assist}"
        val r5 = "${RadiantTeam[4].kills}/${RadiantTeam[4].death}/${RadiantTeam[4].assist}"

        val d1 = "${DireTeam[0].kills}/${DireTeam[0].death}/${DireTeam[0].assist}"
        val d2 = "${DireTeam[1].kills}/${DireTeam[1].death}/${DireTeam[1].assist}"
        val d3 = "${DireTeam[2].kills}/${DireTeam[2].death}/${DireTeam[2].assist}"
        val d4 = "${DireTeam[3].kills}/${DireTeam[3].death}/${DireTeam[3].assist}"
        val d5 = "${DireTeam[4].kills}/${DireTeam[4].death}/${DireTeam[4].assist}"

        val totalRadiantKills = RadiantTeam.map { it.kills }.sum().toString()
        val totalDireKills = DireTeam.map { it.kills }.sum().toString()
        return listOf(r1, r2, r3, r4, r5, d1, d2, d3, d4, d5, totalRadiantKills, totalDireKills)
    }

    fun calculateLineAssign(position: Array<Int>) {
        val arrayBottomRaddiant = ArrayList<HeroStats>()
        val arrayMidRaddiant = ArrayList<HeroStats>()
        val arrayTopRaddiant = ArrayList<HeroStats>()
        val arrayBottomDire = ArrayList<HeroStats>()
        val arrayMidDire = ArrayList<HeroStats>()
        val arrayTopDire = ArrayList<HeroStats>()
        for (i in 0 until 5) {
            when (position[i]) {
                0 -> arrayTopRaddiant.add(RadiantTeam[i])
                1 -> arrayMidRaddiant.add(RadiantTeam[i])
                2 -> arrayBottomRaddiant.add(RadiantTeam[i])
            }
            when (position[5 + i]) {
                3 -> arrayTopDire.add(DireTeam[i])
                4 -> arrayMidDire.add(DireTeam[i])
                5 -> arrayBottomDire.add(DireTeam[i])
            }


        }

        var radiantTop = 0
        var radiantMid = 0
        var radiantBottom = 0

        var direTop = 0
        var direMid = 0
        var direBottom = 0
        scope.launch {
            delay(3000)
        }

        scope.launch {
            for (i in 0 until 3) {
                delay(1000)
                val midLane = calculateLineKills(arrayMidRaddiant, arrayMidDire)
                if (midLane == 2) {
                    radiantMid++
                } else if (midLane == 1) {
                    direMid++
                }
                val topLane = calculateLineKills(arrayTopRaddiant, arrayTopDire)
                if (topLane == 2) {
                    radiantTop++
                } else if (topLane == 1) {
                    direTop++
                }
                val bottomLane = calculateLineKills(arrayBottomRaddiant, arrayBottomDire)
                if (bottomLane == 2) {
                    radiantBottom++
                } else if (bottomLane == 1) {
                    direBottom++
                }
                //sendStats()
                gameCount++
                gameState.postValue(gameCount)
            }
            calculateLineTower(
                radiantMid,
                direMid,
                radiantTowers.mid,
                direTowers.mid,
                radiantTowers,
                direTowers
            )
            calculateLineTower(
                radiantTop,
                direTop,
                radiantTowers.top,
                direTowers.top,
                radiantTowers,
                direTowers
            )
            calculateLineTower(
                radiantBottom,
                direBottom,
                radiantTowers.bot,
                direTowers.bot,
                radiantTowers,
                direTowers
            )
            allTowers.postValue(calculateTowers())
        }
    }


    private fun calculateLineKills(
        radiant: ArrayList<HeroStats>,
        dire: ArrayList<HeroStats>
    ): Int {
        var returningVal = 0

        var sumPointsRad = 0
        for (rad in radiant) {
            sumPointsRad += Heroes.values().find { it.id == radiantHeroes[rad.seq - 1] }?.laining!!
        }
        var sumPointsDire = 0
        for (direSeq in dire) {
            sumPointsDire += Heroes.values()
                .find { it.id == direHeroes[direSeq.seq - 1] }?.laining!!
        }



        if (radiant.isNotEmpty() && dire.isNotEmpty()) {
            val rnds = (0 until (radiant.size + dire.size)).random()
            if (rnds > radiant.size - 1) {
                dire[(rnds - radiant.size)].kills++
                for (i in 0 until dire.size) {
                    if (i != rnds - radiant.size) {
                        dire[i].assist++
                    }
                }
                radiant[(0 until radiant.size).random()].death++
                returningVal = 1
            } else {
                radiant[rnds].kills++
                for (i in 0 until radiant.size) {
                    if (i != rnds) {
                        radiant[i].assist++
                    }
                }
                dire[(0 until dire.size).random()].death++
                returningVal = 2
            }
        } else if (radiant.isNotEmpty() && dire.isEmpty()) {
            returningVal = 2
        } else if (radiant.isEmpty() && dire.isNotEmpty()) {
            returningVal = 1
        }
        allPlayersStats.postValue(assignStats())
        return returningVal
    }

    private fun calculateLineTower(
        radiant: Int,
        dire: Int,
        radTowers: ArrayList<Boolean>,
        diresTower: ArrayList<Boolean>,
        r: Side,
        d: Side
    ) {
        if (radiant > dire) {
            if (diresTower.isNotEmpty()) {
                diresTower.removeAt(diresTower.size - 1)
                if (direTowers.allBuilds[9]) {
                    d.updateAncient(true)
                }
            } else {
                d.updateAncient(false)
            }

        } else if (dire > radiant) {
            if (radTowers.isNotEmpty()) {
                radTowers.removeAt(radTowers.size - 1)
                if (radiantTowers.allBuilds[9]) {
                    r.updateAncient(true)
                }
            } else {
                r.updateAncient(false)
            }

        }
    }

    private fun calculateTowers(): List<Boolean> {
        return listOf(
            radiantTowers.allBuilds[2],
            radiantTowers.allBuilds[1],
            radiantTowers.allBuilds[0],
            radiantTowers.allBuilds[3],
            radiantTowers.allBuilds[4],
            radiantTowers.allBuilds[5],
            radiantTowers.allBuilds[6],
            radiantTowers.allBuilds[7],
            radiantTowers.allBuilds[8],
            radiantTowers.allBuilds[9],
            direTowers.allBuilds[2],
            direTowers.allBuilds[1],
            direTowers.allBuilds[0],
            direTowers.allBuilds[3],
            direTowers.allBuilds[4],
            direTowers.allBuilds[5],
            direTowers.allBuilds[6],
            direTowers.allBuilds[7],
            direTowers.allBuilds[8],
            direTowers.allBuilds[9]
        )
    }
}
