package com.kovalenko.mealdb.features.meals.model.repository

import com.kovalenko.mealdb.features.meals.model.database.MealDao
import com.kovalenko.mealdb.features.meals.model.database.asDomainModel
import com.kovalenko.mealdb.features.meals.model.domain.Meal
import com.kovalenko.mealdb.features.meals.model.network.MealService
import com.kovalenko.mealdb.features.meals.model.network.NetworkMeals
import com.kovalenko.mealdb.features.meals.model.network.asDatabaseModel
import com.kovalenko.mealdb.network.ApiResponse
import com.kovalenko.mealdb.persistence.NetworkBoundResource
import com.kovalenko.mealdb.persistence.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class MealRepository(
    private val mealApi: MealService,
    private val mealDao: MealDao
) {
    fun loadMeals(name: String): Flow<Resource<List<Meal>>> {
        return object : NetworkBoundResource<List<Meal>, NetworkMeals>() {
            override suspend fun saveNetworkResult(item: NetworkMeals) {
                item.meals?.let {
                    mealDao.insertMeals(it.asDatabaseModel())
                }
            }

            override suspend fun shouldFetch(data: List<Meal>?): Boolean {
                return name.isNotEmpty()
            }

            override suspend fun loadFromDb() = if (name.isNotEmpty()) {
                mealDao.getMealsByName(name).map { it.asDomainModel() }
            } else {
                mealDao.getMeals().map { it.asDomainModel() }
            }

            override suspend fun fetchFromNetwork(): Flow<ApiResponse<NetworkMeals>> {
                return mealApi.getMeals(name)
            }
        }.asFlow()
    }

    fun loadMeal(id: String) = mealDao.getMealById(id).map { it.asDomainModel() }
}
