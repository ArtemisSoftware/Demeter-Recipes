package com.artemissoftware.demeterrecipes.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artemissoftware.demeterrecipes.api.models.FoodRecipe
import com.artemissoftware.demeterrecipes.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
        var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}