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


        /**
         * Long Click Listener
         * */
        holder.itemView.favoriteRecipesRowLayout.setOnLongClickListener {

            requireActivity.startActionMode(this)
            true

//            if (!multiSelection) {
//                multiSelection = true
//                requireActivity.startActionMode(this)
//                applySelection(holder, currentRecipe)
//                true
//            } else {
//                multiSelection = false
//                false
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



    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
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
//        myViewHolders.forEach { holder ->
//            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
//        }
//        multiSelection = false
//        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

}