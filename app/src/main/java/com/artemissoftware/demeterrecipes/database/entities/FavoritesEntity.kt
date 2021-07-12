package com.artemissoftware.demeterrecipes.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artemissoftware.demeterrecipes.util.Constants.Companion.FAVORITE_RECIPES_TABLE
import com.artemissoftware.demeterrecipes.api.models.Result

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)