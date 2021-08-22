package com.artemissoftware.demeterrecipes.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.artemissoftware.demeterrecipes.R
import com.artemissoftware.demeterrecipes.api.models.Result
import com.artemissoftware.demeterrecipes.bindingadapters.RecipeItemBinding
import com.artemissoftware.demeterrecipes.bindingadapters.RecipesBinding
import com.artemissoftware.demeterrecipes.databinding.FragmentOverviewBinding
import com.artemissoftware.demeterrecipes.util.Constants.Companion.RECIPE_RESULT_KEY
import org.jsoup.Jsoup


class OverviewFragment : Fragment(R.layout.fragment_overview) {


    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        _binding = FragmentOverviewBinding.bind(view)

        val args = arguments
        val myBundle: Result = args!!.getParcelable<Result>(RECIPE_RESULT_KEY) as Result


        binding.mainImageView.load(myBundle.image)
        binding.titleTextView.text = myBundle.title
        binding.likesTextView.text = myBundle.aggregateLikes.toString()
        binding.timeTextView.text = myBundle.readyInMinutes.toString()


        RecipeItemBinding.parseHtml(binding.summaryTextView, myBundle.summary)

        updateColors(myBundle.vegetarian, binding.vegetarianTextView, binding.vegetarianImageView)
        updateColors(myBundle.vegan, binding.veganTextView, binding.veganImageView)
        updateColors(myBundle.cheap, binding.cheapTextView, binding.cheapImageView)
        updateColors(myBundle.dairyFree, binding.dairyFreeTextView, binding.dairyFreeImageView)
        updateColors(myBundle.glutenFree, binding.glutenFreeTextView, binding.glutenFreeImageView)
        updateColors(myBundle.veryHealthy, binding.healthyTextView, binding.healthyImageView)
    }


    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsOn) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}