package com.artemissoftware.demeterrecipes.api.models

import com.google.gson.annotations.SerializedName

data class FoodJoke(
        @SerializedName("text")
        val text: String
)