package com.artemissoftware.demeterrecipes.ui.fragments.ingredients.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.artemissoftware.demeterrecipes.R
import com.artemissoftware.demeterrecipes.api.models.ExtendedIngredient
import com.artemissoftware.demeterrecipes.databinding.ItemIngredientBinding
import com.artemissoftware.demeterrecipes.ui.fragments.recipes.adapters.RecipesDiffUtil
import com.artemissoftware.demeterrecipes.util.Constants.Companion.BASE_IMAGE_URL
import java.util.*

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.binding.ingredientImageView.load(BASE_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.binding.ingredientName.text = ingredientsList[position].name.capitalize(/*Locale.ROOT*/)
        holder.binding.ingredientAmount.text = ingredientsList[position].amount.toString()
        holder.binding.ingredientUnit.text = ingredientsList[position].unit
        holder.binding.ingredientConsistency.text = ingredientsList[position].consistency
        holder.binding.ingredientOriginal.text = ingredientsList[position].original
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }


    class IngredientViewHolder(val binding: ItemIngredientBinding): RecyclerView.ViewHolder(binding.root)

}