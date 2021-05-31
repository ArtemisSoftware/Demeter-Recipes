package com.artemissoftware.demeterrecipes.api.models

import com.artemissoftware.demeterrecipes.api.models.Result
import com.google.gson.annotations.SerializedName


data class FoodRecipe(
    @SerializedName("results")
    val results: List<Result>
)