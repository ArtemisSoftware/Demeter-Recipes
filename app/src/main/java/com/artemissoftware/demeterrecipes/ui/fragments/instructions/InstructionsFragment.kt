package com.artemissoftware.demeterrecipes.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.artemissoftware.demeterrecipes.R
import com.artemissoftware.demeterrecipes.api.models.Result
import com.artemissoftware.demeterrecipes.databinding.FragmentInstructionsBinding
import com.artemissoftware.demeterrecipes.util.Constants


class InstructionsFragment : Fragment(R.layout.fragment_instructions) {


    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        _binding = FragmentInstructionsBinding.bind(view)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        binding.instructionsWebView.webViewClient = object : WebViewClient() {}
        val websiteUrl: String = myBundle!!.sourceUrl
        binding.instructionsWebView.loadUrl(websiteUrl)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}