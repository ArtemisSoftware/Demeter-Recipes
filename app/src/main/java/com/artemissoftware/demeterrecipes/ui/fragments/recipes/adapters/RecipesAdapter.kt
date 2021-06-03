package com.artemissoftware.demeterrecipes.ui.fragments.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.artemissoftware.demeterrecipes.api.models.FoodRecipe
import com.artemissoftware.demeterrecipes.databinding.ItemRecipeBinding
import com.artemissoftware.demeterrecipes.api.models.Result

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipeHolder>() {

    private var recipes = emptyList<Result>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        return RecipeHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: FoodRecipe){
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }


    class RecipeHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result){
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecipeHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecipeBinding.inflate(layoutInflater, parent, false)
                return RecipeHolder(binding)
            }
        }

    }

}