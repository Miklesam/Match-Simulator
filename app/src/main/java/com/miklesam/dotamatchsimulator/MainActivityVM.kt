package com.miklesam.dotamatchsimulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityVM : ViewModel() {


    private val progress = MutableLiveData<Int>()
    fun getProgress(): LiveData<Int> = progress

    init {
        progress.value = 0
    }

    fun setProgress(progres: Int) {
        progress.value=progres
    }


}