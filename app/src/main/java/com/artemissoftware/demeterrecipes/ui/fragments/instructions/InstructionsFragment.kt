package com.artemissoftware.demeterrecipes.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.artemissoftware.demeterrecipes.R
import com.artemissoftware.demeterrecipes.api.models.Result
import com.artemissoftware.demeterrecipes.util.Constants
import kotlinx.android.synthetic.main.fragment_instructions.*


class InstructionsFragment : Fragment(R.layout.fragment_instructions) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        instructions_webView.webViewClient = object : WebViewClient() {}
        val websiteUrl: String = myBundle!!.sourceUrl
        instructions_webView.loadUrl(websiteUrl)
    }

}