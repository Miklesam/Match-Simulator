package com.miklesam.dotamatchsimulator.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.miklesam.dotamatchsimulator.R


fun Fragment.showCustomToast(message: String, long: Int) {
    val toast = Toast(this.context)
    toast.duration = Toast.LENGTH_LONG
    val inflater =
        context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view: View = inflater.inflate(R.layout.your_custom_layout, null)
    val customToastText = view.findViewById<TextView>(R.id.customToastText)
    toast.view = view
    customToastText.text = message
    toast.duration = long
    toast.show()
}



fun Fragment.hideKeyboard() {
    val view = this.activity?.currentFocus
    if (view != null) {
        val imm =
            this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun View.Visible() {
    visibility = View.VISIBLE
}

fun View.InVisible() {
    visibility = View.INVISIBLE
}

fun View.Gone() {
    visibility = View.GONE
}

fun View.setVisibility(state: Boolean?) {
    visibility = when (state) {
        true -> View.VISIBLE
        false -> View.INVISIBLE
        null -> View.GONE
    }
}

fun AppCompatActivity.replaceFragmentFromRightToLeft(fragment: Fragment, addToBackStack: Boolean) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.setCustomAnimations(
        R.anim.enter_right_to_left,
        R.anim.exit_right_to_left,
        R.anim.enter_left_to_right,
        R.anim.exit_left_to_right
    )
    transaction.replace(R.id.fragment_holder, fragment)
    if (addToBackStack) transaction.addToBackStack(null)
    transaction.commit()
}

