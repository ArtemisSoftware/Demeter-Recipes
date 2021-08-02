package com.artemissoftware.demeterrecipes.ui.fragments.foodjoke

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.artemissoftware.demeterrecipes.R
import com.artemissoftware.demeterrecipes.databinding.FragmentFoodJokeBinding
import com.artemissoftware.demeterrecipes.ui.MainViewModel
import com.artemissoftware.demeterrecipes.util.Constants.Companion.API_KEY
import com.artemissoftware.demeterrecipes.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment(R.layout.fragment_food_joke) {

    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    private var foodJoke = "No Food Joke"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        _binding = FragmentFoodJokeBinding.bind(view)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        setHasOptionsMenu(true)

        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->

            when(response){
                is NetworkResult.Success -> {
                    binding.foodJokeTextView.text = response.data?.text
                    if(response.data != null){
                        foodJoke = response.data.text
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("FoodJokeFragment", "Loading")
                }
            }
        }
    }



    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner) { database->
                if(!database.isNullOrEmpty()){
                    binding.foodJokeTextView.text = database[0].foodJoke.text
                    foodJoke = database[0].foodJoke.text
                }
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share_food_joke_menu){
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, foodJoke)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}