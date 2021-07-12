package com.artemissoftware.demeterrecipes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artemissoftware.demeterrecipes.database.converters.RecipesTypeConverter
import com.artemissoftware.demeterrecipes.database.dao.RecipesDao
import com.artemissoftware.demeterrecipes.database.entities.FavoritesEntity
import com.artemissoftware.demeterrecipes.database.entities.RecipesEntity

@Database(
        entities = [RecipesEntity::class, FavoritesEntity::class],
        version = 2,
        exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}