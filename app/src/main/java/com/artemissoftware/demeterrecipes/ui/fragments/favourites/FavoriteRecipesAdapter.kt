package com.artemissoftware.demeterrecipes.ui.fragments.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.artemissoftware.demeterrecipes.database.entities.FavoritesEntity
import com.artemissoftware.demeterrecipes.databinding.ItemFavoriteRecipeBinding
import com.artemissoftware.demeterrecipes.ui.fragments.recipes.adapters.RecipesDiffUtil
import kotlinx.android.synthetic.main.item_favorite_recipe.view.*

class FavoriteRecipesAdapter : RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteHolder>() {

    private var favoriteRecipes = emptyList<FavoritesEntity>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val selectedRecipe = favoriteRecipes[position]
        holder.bind(selectedRecipe)

        /**
         * Single Click Listener
         * */
        holder.itemView.favoriteRecipesRowLayout.setOnClickListener {
//            if (multiSelection) {
//                applySelection(holder, currentRecipe)
//            } else {
                val action = FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(selectedRecipe.result)
                holder.itemView.findNavController().navigate(action)
//            }
        }


    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>){
        val favoriteRecipesDiffUtil = RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }


    class FavoriteHolder(private val binding: ItemFavoriteRecipeBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFavoriteRecipeBinding.inflate(layoutInflater, parent, false)
                return FavoriteHolder(binding)
            }
        }

    }

}