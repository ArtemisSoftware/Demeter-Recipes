package com.artemissoftware.demeterrecipes.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.artemissoftware.demeterrecipes.ui.MainViewModel
import com.artemissoftware.demeterrecipes.R
import com.artemissoftware.demeterrecipes.ui.fragments.recipes.adapters.RecipesAdapter
import com.artemissoftware.demeterrecipes.util.NetworkResult
import com.artemissoftware.demeterrecipes.util.extensions.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment(R.layout.fragment_recipes) {


    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    private val recipesAdapter by lazy { RecipesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

        setupRecyclerView()
        readDatabase()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, Observer { database ->
                if (database.isEmpty()) {
                    Log.d("RecipesFragment", "Read Database called")
                    recipesAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }



    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData called!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())

        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { recipesAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }

            }
        }
    }


    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database->
                if (database.isNotEmpty()) {
                    recipesAdapter.setData(database[0].foodRecipe)
                }
            }
        }
    }


    private fun setupRecyclerView() = recyclerview.apply {
        adapter = recipesAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }



    private fun showShimmerEffect() {
        recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
        recyclerview.hideShimmer()
    }

}