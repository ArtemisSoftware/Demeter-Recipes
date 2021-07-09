package com.artemissoftware.demeterrecipes.ui.fragments.ingredients.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.artemissoftware.demeterrecipes.R
import com.artemissoftware.demeterrecipes.api.models.ExtendedIngredient
import com.artemissoftware.demeterrecipes.ui.fragments.recipes.adapters.RecipesDiffUtil
import com.artemissoftware.demeterrecipes.util.Constants.Companion.BASE_IMAGE_URL
import kotlinx.android.synthetic.main.item_ingredient.view.*
import java.util.*

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.itemView.ingredient_imageView.load(BASE_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.itemView.ingredient_name.text = ingredientsList[position].name.capitalize(/*Locale.ROOT*/)
        holder.itemView.ingredient_amount.text = ingredientsList[position].amount.toString()
        holder.itemView.ingredient_unit.text = ingredientsList[position].unit
        holder.itemView.ingredient_consistency.text = ingredientsList[position].consistency
        holder.itemView.ingredient_original.text = ingredientsList[position].original
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


    class IngredientViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}