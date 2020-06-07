package com.miklesam.dotamanager.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import com.miklesam.dotamatchsimulator.R

class EndMatchDialog() : AppCompatDialogFragment() {
    constructor(myListener: toLobbyInterface, side: Int) : this() {
        mListener = myListener
        sude = side
    }


    var Lock = true
    var sude = 0
    var mListener: toLobbyInterface? = null

    interface toLobbyInterface {
        fun goToLobbyClick()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val mycustomview = inflater.inflate(R.layout.layout_end_match_dialog, null)
        val match_result_text = mycustomview.findViewById<TextView>(R.id.match_result_text)
        when (sude) {
            1 -> {
                match_result_text.text = "You Win"
                match_result_text.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.win
                    )
                )
            }
            2 -> {
                match_result_text.text = "You Lose"
                match_result_text.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.lose
                    )
                )
            }
            3 -> {
                match_result_text.text = "Draw"
                match_result_text.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.draw
                    )
                )
            }
        }




        builder.setView(mycustomview)
        builder.setTitle("Match is over")
        builder.setPositiveButton("Quit") { _, _ ->
            mListener?.goToLobbyClick()
            Lock = false
        }

        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}