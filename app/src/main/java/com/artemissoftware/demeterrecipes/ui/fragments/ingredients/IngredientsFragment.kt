package com.artemissoftware.demeterrecipes.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.artemissoftware.demeterrecipes.R
import com.artemissoftware.demeterrecipes.api.models.Result
import com.artemissoftware.demeterrecipes.ui.fragments.ingredients.adapters.IngredientsAdapter
import com.artemissoftware.demeterrecipes.util.Constants.Companion.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.fragment_ingredients.*


class IngredientsFragment : Fragment(R.layout.fragment_ingredients) {

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setupRecyclerView()
        myBundle?.extendedIngredients?.let { ingredientsAdapter.setData(it) }
    }


    private fun setupRecyclerView() {
        ingredients_recyclerview.adapter = ingredientsAdapter
        ingredients_recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }
}