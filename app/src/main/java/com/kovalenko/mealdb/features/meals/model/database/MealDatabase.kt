package com.kovalenko.mealdb.features.meals.model.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM databasemeal ORDER BY name ASC")
    fun getMeals(): Flow<List<DatabaseMeal>>

    @Query("SELECT * FROM databasemeal WHERE name LIKE '%' || :name || '%' ORDER BY name ASC")
    fun getMealsByName(name: String): Flow<List<DatabaseMeal>>

    @Query("SELECT * FROM databasemeal WHERE id=:id")
    fun getMealById(id: String): Flow<DatabaseMeal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(meals: List<DatabaseMeal>)
}

@Database(entities = [DatabaseMeal::class], version = 1)
abstract class MealDatabase : RoomDatabase() {
    abstract val mealDao: MealDao
}
