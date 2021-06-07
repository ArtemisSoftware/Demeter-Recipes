package com.artemissoftware.demeterrecipes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artemissoftware.demeterrecipes.database.converters.RecipesTypeConverter
import com.artemissoftware.demeterrecipes.database.dao.RecipesDao
import com.artemissoftware.demeterrecipes.database.entities.RecipesEntity

@Database(
        entities = [RecipesEntity::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}