package com.miklesam.dotamatchsimulator.utils

import com.miklesam.dotamanager.datamodels.HeroStats
import com.miklesam.dotamatchsimulator.datamodels.Heroes

class LaneCalculator {
    fun calculateLineKills(
        radiant: ArrayList<HeroStats>,
        dire: ArrayList<HeroStats>,
        radiantHeroes: ArrayList<Int>,
        direHeroes: ArrayList<Int>,
        gameCount: Int
    ): Int {
        var returningVal = 0
        if (radiant.isNotEmpty() && dire.isNotEmpty()) {
            var sumPointsRad = 0
            for (rad in radiant) {
                sumPointsRad += when {
                    gameCount < 6 -> {
                        Heroes.values()
                            .find { it.id == radiantHeroes[rad.seq - 1] }?.laining!!
                    }
                    gameCount < 12 -> {
                        Heroes.values()
                            .find { it.id == radiantHeroes[rad.seq - 1] }?.fighting!!
                    }
                    else -> {
                        Heroes.values()
                            .find { it.id == radiantHeroes[rad.seq - 1] }?.lateGame!!
                    }
                }
            }
            var sumPointsDire = 0
            for (direSeq in dire) {
                sumPointsDire += when {
                    gameCount < 6 -> {
                        Heroes.values()
                            .find { it.id == direHeroes[direSeq.seq - 1] }?.laining!!
                    }
                    gameCount < 12 -> {
                        Heroes.values()
                            .find { it.id == direHeroes[direSeq.seq - 1] }?.fighting!!
                    }
                    else -> {
                        Heroes.values()
                            .find { it.id == direHeroes[direSeq.seq - 1] }?.lateGame!!
                    }
                }
            }

            if (sumPointsRad > sumPointsDire) {
                //radiant makes kill
                val differ = sumPointsRad - sumPointsDire
                val firstPoint = calculateFirstPoint(radiant)
                val secondPoint = calculateSecondPoint(radiant)
                if (differ <= firstPoint) {
                    generateRadiantKill(radiant, dire)
                    generateDiretKill(radiant, dire)
                } else if (differ <= secondPoint) {
                    generateRadiantKill(radiant, dire)
                    if (radiant.size == 1 && dire.size == 1) returningVal = 2
                } else {
                    generateRadiantKill(radiant, dire)
                    returningVal = 2
                }
            } else if (sumPointsDire > sumPointsRad) {
                //dire makes kill
                val differ = sumPointsDire - sumPointsRad
                val firstPoint = calculateFirstPoint(dire)
                val secondPoint = calculateSecondPoint(dire)
                if (differ <= firstPoint) {
                    generateRadiantKill(radiant, dire)
                    generateDiretKill(radiant, dire)
                } else if (differ <= secondPoint) {
                    generateDiretKill(radiant, dire)
                    if (radiant.size == 1 && dire.size == 1) returningVal = 1
                } else {
                    generateDiretKill(radiant, dire)
                    returningVal = 1
                }
            } else {
                //nobody makes kill
                returningVal = 0
            }
        } else if (radiant.isNotEmpty() && dire.isEmpty()) {
            returningVal = 2
        } else if (radiant.isEmpty() && dire.isNotEmpty()) {
            returningVal = 1
        }

        return returningVal
    }


    private fun generateRadiantKill(
        radiant: ArrayList<HeroStats>,
        dire: ArrayList<HeroStats>
    ) {
        val rnds = (0 until (radiant.size)).random()
        radiant[rnds].kills++
        for (i in 0 until radiant.size) {
            if (i != rnds) {
                radiant[i].assist++
            }
        }
        dire[(0 until dire.size).random()].death++
    }


    private fun generateDiretKill(
        radiant: ArrayList<HeroStats>,
        dire: ArrayList<HeroStats>
    ) {
        val rnds = (0 until (dire.size)).random()
        dire[rnds].kills++
        for (i in 0 until dire.size) {
            if (i != rnds) {
                dire[i].assist++
            }
        }
        radiant[(0 until radiant.size).random()].death++
    }


    private fun calculateFirstPoint(heroesOnLine: ArrayList<HeroStats>): Int {
        var firstPoint = 0
        firstPoint = 10 + (heroesOnLine.size - 1) * 15
        if (heroesOnLine.size == 5) {
            firstPoint += 20
        }
        return firstPoint
    }

    private fun calculateSecondPoint(heroesOnLine: ArrayList<HeroStats>): Int {
        var secondPoint = 0
        secondPoint = 15 + (heroesOnLine.size - 1) * 20
        if (heroesOnLine.size == 5) {
            secondPoint += 50
        }
        return secondPoint
    }


}