package com.artemissoftware.demeterrecipes.ui.fragments.favourites

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artemissoftware.demeterrecipes.R
import com.artemissoftware.demeterrecipes.databinding.FragmentFavoriteRecipesBinding
import com.artemissoftware.demeterrecipes.ui.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment(R.layout.fragment_favorite_recipes) {

    private val mainViewModel: MainViewModel by viewModels()
    private val favoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity(), mainViewModel) }



    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        _binding = FragmentFavoriteRecipesBinding.bind(view)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.favoriteRecipesAdapter = favoriteRecipesAdapter

        setHasOptionsMenu(true)

        setupRecyclerView(binding.favoriteRecipesRecyclerView)

        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner, {
            favoriteRecipesAdapter.setData(it)
        })

    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = favoriteRecipesAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.deleteAll_favorite_recipes_menu){
            mainViewModel.deleteAllFavoriteRecipes()
            showSnackBar()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar(){
        Snackbar.make(
                binding.root,
                "All recipes removed.",
                Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
                .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        favoriteRecipesAdapter.clearContextualActionMode()
    }
}