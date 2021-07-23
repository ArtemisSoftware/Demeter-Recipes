package com.artemissoftware.demeterrecipes.ui.fragments.favourites

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.artemissoftware.demeterrecipes.R
import com.artemissoftware.demeterrecipes.database.entities.FavoritesEntity
import com.artemissoftware.demeterrecipes.databinding.ItemFavoriteRecipeBinding
import com.artemissoftware.demeterrecipes.ui.fragments.recipes.adapters.RecipesDiffUtil
import kotlinx.android.synthetic.main.item_favorite_recipe.view.*

class FavoriteRecipesAdapter(private val requireActivity: FragmentActivity) : RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteHolder>(), ActionMode.Callback {

    private var favoriteRecipes = emptyList<FavoritesEntity>()

    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoritesEntity>()
    private var favoriteHolder = arrayListOf<FavoriteHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {

        favoriteHolder
        val currentRecipe = favoriteRecipes[position]
        holder.bind(currentRecipe)

        /**
         * Single Click Listener
         * */
        holder.itemView.favoriteRecipesRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action = FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(currentRecipe.result)
                holder.itemView.findNavController().navigate(action)
            }
        }


        /**
         * Long Click Listener
         * */
        holder.itemView.favoriteRecipesRowLayout.setOnLongClickListener {

            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                multiSelection = false
                false
            }

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



    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun applySelection(holder: FavoriteHolder, currentRecipe: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            //applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            //applyActionModeTitle()
        }
    }


    private fun changeRecipeStyle(holder: FavoriteHolder, backgroundColor: Int, strokeColor: Int) {
        holder.itemView.favoriteRecipesRowLayout.setBackgroundColor(ContextCompat.getColor(requireActivity, backgroundColor))
        holder.itemView.favorite_row_cardView.strokeColor = ContextCompat.getColor(requireActivity, strokeColor)
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

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
//        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
//        if (menu?.itemId == R.id.delete_favorite_recipe_menu) {
//            selectedRecipes.forEach {
//                mainViewModel.deleteFavoriteRecipe(it)
//            }
//            showSnackBar("${selectedRecipes.size} Recipe/s removed.")
//
//            multiSelection = false
//            selectedRecipes.clear()
//            actionMode?.finish()
//        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        favoriteHolder.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

}