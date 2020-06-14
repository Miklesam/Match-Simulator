package com.miklesam.dotamatchsimulator.simplefragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.miklesam.dotamatchsimulator.R
import kotlinx.android.synthetic.main.fragment_privacy_policy.*

class FragmentPrivacyPolicy :Fragment(R.layout.fragment_privacy_policy){


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        my_web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        my_web.loadUrl("https://github.com/Miklesam/Match-Simulator/blob/master/README.md/")
    }
}