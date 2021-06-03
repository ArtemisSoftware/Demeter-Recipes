package com.artemissoftware.demeterrecipes.data

import com.artemissoftware.demeterrecipes.api.FoodRecipesApi
import com.artemissoftware.demeterrecipes.api.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

}