package com.artemissoftware.demeterrecipes.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.artemissoftware.demeterrecipes.R
import com.artemissoftware.demeterrecipes.api.models.Result
import com.artemissoftware.demeterrecipes.databinding.FragmentIngredientsBinding
import com.artemissoftware.demeterrecipes.ui.fragments.ingredients.adapters.IngredientsAdapter
import com.artemissoftware.demeterrecipes.util.Constants.Companion.RECIPE_RESULT_KEY


class IngredientsFragment : Fragment(R.layout.fragment_ingredients) {

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        _binding = FragmentIngredientsBinding.bind(view)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setupRecyclerView()
        myBundle?.extendedIngredients?.let { ingredientsAdapter.setData(it) }
    }


    private fun setupRecyclerView() {
        binding.ingredientsRecyclerview.adapter = ingredientsAdapter
        binding.ingredientsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}